import net.paulem.buildscript.NeoForgeMutex
import net.paulem.buildscript.NewGithubChangelog
import net.paulem.buildscript.VersionRangeParser

plugins {
    id("net.neoforged.moddev") version "2.0.148"

    `maven-publish`
    id("me.modmuss50.mod-publish-plugin") version "2.2.1"

    id("dev.kikugie.stonecutter")
}

// "1.21.11-neoforge" -> "1.21.11-fabric": the matching Fabric node runs the datagen, its output is reused here
val fabricNode = stonecutter.current.project.replace("-neoforge", "-fabric")

version = "${stonecutter.properties.get<String>("mod.version")}-${stonecutter.current.project}"
group = stonecutter.properties.get<String>("maven_group")

base {
    archivesName.set(stonecutter.properties.get<String>("mod.id"))
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
    maven { url = uri("https://api.modrinth.com/maven") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.shedaniel.me/") }
    maven("https://maven.midnightdust.eu/releases")
    maven {
        name = "paulemReleases"
        url = uri("https://maven.paulem.net/releases")
    }
}

fun depOrNull(depName: String): String? = stonecutter.properties.getOrNull<String>("deps.$depName")

val neoVersion: String = depOrNull("neoforge") ?: error("deps.neoforge manquant pour ${stonecutter.current.project}")
val hasBucketlib: Boolean = depOrNull("bucketlib") != null
val afterDeobf = stonecutter.eval(stonecutter.current.version, ">1.21.11")

val minecraftVersion: String = depOrNull("minecraft") ?: stonecutter.current.version
val isSnapshot = VersionRangeParser.isSnapshotId(minecraftVersion) || neoVersion.endsWith("-beta")

val javaversion = if (stonecutter.eval(stonecutter.current.version, ">=26.1"))
    JavaVersion.VERSION_25 else JavaVersion.VERSION_21

java {
    sourceCompatibility = javaversion
    targetCompatibility = javaversion
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(javaversion.toString().toInt())
    options.encoding = "UTF-8"
}

// Client source set: compiled against main, like Loom's "splitEnvironmentSourceSets"
val clientSourceSet: SourceSet = sourceSets.create("client") {
    compileClasspath += sourceSets.main.get().output + sourceSets.main.get().compileClasspath
    runtimeClasspath += sourceSets.main.get().output + sourceSets.main.get().runtimeClasspath
}

sourceSets {
    all {
        // Fabric-only code (entrypoints, datagen, Mod Menu) is only compiled by the Fabric nodes
        java.exclude("**/fabric/**", "net/paulem/simpleores/datagen/**", "net/paulem/simpleores/config/ModMenuCompat.java")
    }

    main {
        resources {
            srcDirs(
                // Fabric datagen output, shared with NeoForge
                rootProject.file("versions/$fabricNode/src/main/generated"),
                rootProject.file("versions/$fabricNode/src/main/resources"),
                project.file("versions/${stonecutter.current.project}/src/main/resources"),
                // On NeoForge the ores are added by biome modifiers, not code
                rootProject.file("sc-resources/neoforge/resources"),
                if (hasBucketlib) rootProject.file("sc-resources/main/hasbucketlib")
                else rootProject.file("sc-resources/main/nobucketlib")
            )
        }
    }
}

// Chest loot of the base mod: one template set, adapted to the version (folder name and copper equipment)
val chestLootDir = layout.buildDirectory.dir("generated/chest-loot")
val generateChestLoot by tasks.registering(Copy::class) {
    val singularFolder = stonecutter.eval(stonecutter.current.version, ">=1.21")
    val maxVersionRange = stonecutter.properties.getOrNull<String>("max_version_range") ?: "1.21.8"
    val vanillaCopperTools = afterDeobf || stonecutter.eval(maxVersionRange, ">1.21.8")
    val copperTools = Regex("simpleores:copper_(axe|boots|chestplate|helmet|leggings|pickaxe|shovel|sword)")

    from(rootProject.file("sc-resources/chest-loot"))
    into(chestLootDir.map { it.dir("data/simpleores/${if (singularFolder) "loot_table" else "loot_tables"}/chest") })
    if (vanillaCopperTools) filter { line -> copperTools.replace(line, "minecraft:copper_$1") }
}
sourceSets.main { resources.srcDir(chestLootDir) }
tasks.processResources { dependsOn(generateChestLoot) }

// NeoForm uses a lot of memory, so only one NeoForge node creates its Minecraft artifacts at a time
val neoForgeMutex = gradle.sharedServices.registerIfAbsent("neoforge-mutex", NeoForgeMutex::class.java) {
    maxParallelUsages.set(1)
}

tasks.matching { it.name == "createMinecraftArtifacts" }.configureEach {
    usesService(neoForgeMutex)
}

// NeoForge doesn't read Fabric access wideners, so the same entries are given as access transformers
// Versions with BucketLib only need the furnace entry, except 26.x where the mixins use them too
val atFile = if (hasBucketlib && !afterDeobf) "at-minimal.cfg" else "at-full.cfg"

// The enchanted offers class isn't public before 1.21.5 (Fabric widens it with its own access widener)
val atFiles = buildList {
    add(rootProject.file("sc-resources/neoforge/$atFile"))
    if (stonecutter.eval(stonecutter.current.version, "<=1.21.3")) add(rootProject.file("sc-resources/neoforge/at-legacy-trades.cfg"))
}

neoForge {
    version = neoVersion

    runs {
        register("client") {
            client()
            systemProperty("neoforge.enabledGameTestNamespaces", "simpleores")
        }
        register("server") {
            server()
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", "simpleores")
        }
    }

    mods {
        register("simpleores") {
            sourceSet(sourceSets.main.get())
            sourceSet(clientSourceSet)
        }
    }

    addModdingDependenciesTo(clientSourceSet)

    // See sc-resources/neoforge
    accessTransformers.from(atFiles)
}

val minecraftRange: String = run {
    val range = stonecutter.properties.getOrNull<String>("version_range")
    if (range != null) {
        // "~26.2-": the 26.2 line and its hotfixes
        val line = Regex("^~(\\d+)\\.(\\d+)-$").find(range)
        if (line != null) {
            val (major, minor) = line.destructured
            "[$major.$minor,$major.${minor.toInt() + 1})"
        } else range
    } else {
        val min = stonecutter.properties.get<String>("min_version_range")
        val max = stonecutter.properties.get<String>("max_version_range")
        // A maven range can't have two identical bounds
        if (min == max) "[$min]" else "[$min,$max]"
    }
}

// A single META-INF/accesstransformer.cfg in the jar, so the files are concatenated
val mergeAccessTransformers by tasks.registering {
    val output = layout.buildDirectory.file("generated/accesstransformer/accesstransformer.cfg")
    inputs.files(atFiles)
    outputs.file(output)

    doLast {
        output.get().asFile.apply {
            parentFile.mkdirs()
            writeText(atFiles.joinToString(System.lineSeparator()) { it.readText().trim() } + System.lineSeparator())
        }
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    // Fabric files / datagen cache are useless in a NeoForge jar
    exclude("fabric.mod.json", "accesswideners/**", "**/.cache/**", "simpleores_client.mixins.json")

    from(mergeAccessTransformers) {
        into("META-INF")
    }

    val optionalDependencies = buildString {
        fun dependency(id: String, type: String) {
            appendLine()
            appendLine("[[dependencies.simpleores]]")
            appendLine("modId = \"$id\"")
            appendLine("type = \"$type\"")
            appendLine("versionRange = \"*\"")
            appendLine("ordering = \"NONE\"")
            appendLine("side = \"BOTH\"")
        }

        if (hasBucketlib) dependency("bucketlib", "required")
        if (depOrNull("midnightlib") != null) dependency("midnightlib", "optional")
    }

    val expandProps = mapOf(
        "version" to version,
        "neoforge_version" to neoVersion,
        "mc_range" to minecraftRange,
        "optional_dependencies" to optionalDependencies,
        "compatibility_level" to "JAVA_${javaversion.ordinal + 1}",
    )

    // Energized Power pulverizer recipes have a Fabric condition, add the NeoForge one
    filesMatching("data/simpleores/recipe*/pulverizer/*.json") {
        filter { line: String ->
            if (line.contains("\"type\": \"energizedpower:pulverizer\"")) {
                line + "\n  \"neoforge:conditions\": [{\"type\": \"neoforge:mod_loaded\", \"modid\": \"energizedpower\"}],"
            } else line
        }
    }

    // Until 1.21.11 the render layer comes from the model (Fabric registers it in code, NeoForge reads the JSON)
    // From 26.1 it is deduced from the textures. Doors and bars must be "cutout"
    if (!afterDeobf) {
        filesMatching(listOf("assets/simpleores/models/block/*_door_*.json", "assets/simpleores/models/block/*_bars_*.json")) {
            filter { line: String ->
                if (line == "{") line + "\n  \"render_type\": \"minecraft:cutout\"," else line
            }
        }
    }

    filesMatching(listOf("META-INF/neoforge.mods.toml", "*.mixins.json")) {
        expand(expandProps)
    }
    inputs.properties(expandProps)
}

tasks.jar {
    from(clientSourceSet.output)

    from("LICENSE") {
        rename { "${it}_${project.base.archivesName.get()}" }
    }
}

dependencies {
    depOrNull("midnightlib")?.let { midnightlibVersion ->
        // Versions without "+" are only published on Modrinth
        val midnightlib = if (midnightlibVersion.contains("+"))
            "eu.midnightdust:midnightlib:$midnightlibVersion"
        else
            "maven.modrinth:midnightlib:$midnightlibVersion"

        val excludeModMenu: ExternalModuleDependency.() -> Unit = {
            exclude(group = "com.terraformersmc", module = "modmenu")
        }

        compileOnly(midnightlib, excludeModMenu)
        "clientCompileOnly"(midnightlib, excludeModMenu)
        runtimeOnly(midnightlib, excludeModMenu)
    }

    depOrNull("bucketlib")?.let { bucketlibVersion ->
        depOrNull("cloth_config")?.let {
            implementation("me.shedaniel.cloth:cloth-config-neoforge:$it")
        }
        implementation("com.github.cech12.BucketLib:neoforge:$bucketlibVersion")
    }

    "clientImplementation"(sourceSets.main.get().output)
}

// --- Publication ----------------------------------------------------------------------------------

fun secret(name: String): String? =
    ((findProperty(name) as String?) ?: System.getenv(name))?.takeIf { it.isNotBlank() }

val githubToken = secret("GH_RELEASE_TOKEN") ?: secret("GITHUB_TOKEN")
val githubChangelog: String = try {
    NewGithubChangelog.getChangelog(project.rootDir.toPath(), githubToken)
} catch (e: Exception) {
    project.logger.warn("Impossible de récupérer le changelog de GitHub : ${e.message}")
    "Changelog is currently unavailable."
}

val versionRangeProperties: Map<String, String> = buildMap {
    stonecutter.properties.getOrNull<String>("version_range")?.let { put("version_range", it) }
    stonecutter.properties.getOrNull<String>("min_version_range")?.let { put("min_version_range", it) }
    stonecutter.properties.getOrNull<String>("max_version_range")?.let { put("max_version_range", it) }
}

fun curseforgeVersions(): List<String> {
    val override = stonecutter.properties.getOrNull<String>("curseforge_versions")
        ?.split(',')
        ?.map(String::trim)
        ?.filter(String::isNotEmpty)
        ?: emptyList()

    if (override.isNotEmpty()) return override

    return if (isSnapshot) {
        listOf(VersionRangeParser.toCurseforgeVersion(minecraftVersion))
    } else {
        VersionRangeParser.parseVersionRange(
            versionRangeProperties,
            VersionRangeParser.CompiledVersions.VersionType.RELEASE
        )
    }
}

publishing {
    repositories {
        maven {
            name = "paulem"
            url = uri("https://maven.paulem.net/releases")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "${base.archivesName.get()}-neoforge"
            version = project.version.toString()
            from(components["java"])
        }
    }
}

// CI: -PprebuiltJarsDir=<dir> publishes already built jars without compiling
val prebuiltJar: File? = providers.gradleProperty("prebuiltJarsDir").orNull
    ?.let { File(it, "${base.archivesName.get()}-${project.version}.jar") }

publishMods {
    // ./gradlew publishMods -PpublishDryRun: generates the requests without sending anything
    dryRun = providers.gradleProperty("publishDryRun").isPresent

    file.set(prebuiltJar?.let { layout.file(provider { it }) } ?: tasks.jar.flatMap { it.archiveFile })

    displayName.set("SimpleOres NeoForge ${stonecutter.properties.get<String>("mod.version")} for $minecraftVersion")
    version.set("${stonecutter.properties.get<String>("mod.version")}-neoforge-$minecraftVersion")
    changelog.set(githubChangelog)


    type.set(
        if (isSnapshot || !hasBucketlib) BETA
        else STABLE
    )

    modLoaders.add("neoforge")

    val versions = VersionRangeParser.parseVersionRange(versionRangeProperties)

    modrinth {
        projectId.set("Boe3chj8")
        accessToken.set(secret("MODRINTH_TOKEN"))

        minecraftVersions.addAll(versions)

        optional("midnightlib")
        optional("energized-power")

        if (hasBucketlib) requires("bucketlib")
        if (hasBucketlib && depOrNull("cloth_config") != null) requires("cloth-config")
    }

    curseforge {
        projectId.set("1092987")
        accessToken.set(secret("CURSEFORGE_TOKEN"))

        client.set(true)
        server.set(true)

        minecraftVersions.addAll(curseforgeVersions())

        optional("midnightlib")
        optional("energized-power")

        if (hasBucketlib) requires("bucketlib")
        if (hasBucketlib && depOrNull("cloth_config") != null) requires("cloth-config")
    }
}
