import net.paulem.buildscript.NewGithubChangelog
import net.paulem.buildscript.VersionRangeParser
import org.gradle.api.artifacts.ExternalModuleDependency

plugins {
    // Applies the right Loom variant depending on the version
    id("dev.kikugie.loom-back-compat")

    `maven-publish`
    id("me.modmuss50.mod-publish-plugin") version "2.2.1"

    id("dev.kikugie.stonecutter")
}

// 1. Execution context (independent from the Loom plugin used by loom-back-compat)
val isDeobf = stonecutter.eval(stonecutter.current.version, ">1.21.11")
val isMojmaps = !isDeobf

version = "${stonecutter.properties.get<String>("mod.version")}-${stonecutter.current.project}"
group = stonecutter.properties.get<String>("maven_group")

base {
    archivesName.set(stonecutter.properties.get<String>("mod.id"))
}

repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://api.modrinth.com/maven") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.shedaniel.me/") }
    maven { url = uri("https://maven.architectury.dev/") }
    maven {
        name = "paulemReleases"
        url = uri("https://maven.paulem.net/releases")
    }
    maven("https://maven.nucleoid.xyz/") { name = "Nucleoid" }
    maven("https://maven.midnightdust.eu/releases")
}

val hasBucketlib: Boolean = stonecutter.properties.getOrNull<String>("deps.bucketlib") != null
val containsBucket = stonecutter.eval(stonecutter.current.version, ">1.19.4")

// 2. Conditional Access Widener name
val awSuffix = if (isDeobf) "-deobf.accesswidener" else ".accesswidener"
val accesswidener = when {
    hasBucketlib -> "hasbucketlib$awSuffix"
    containsBucket -> "nobucketlib$awSuffix"
    else -> "nobucket$awSuffix"
}

// A single Loom variant is applied, so the "loom" block resolves directly
loom {
    splitEnvironmentSourceSets()

    mods {
        register("simpleores") {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.getByName("client"))
        }
    }

    runConfigs.all {
        isIdeConfigGenerated = true
        runDirectory.set(project.file("run")) // Syntaxe d'API Property Gradle
    }

    accessWidenerPath = project.rootProject.file("src/main/resources/accesswideners/$accesswidener")
}

val javaversion = if (stonecutter.eval(stonecutter.current.version, ">=26.1"))
    JavaVersion.VERSION_25 else if (stonecutter.eval(stonecutter.current.version, ">=1.20.5"))
    JavaVersion.VERSION_21 else JavaVersion.VERSION_17

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    val bucketlibExpansion = "\", \"bucketlib\": \"*"
    val clientMixinExpansion = "" //"\", \"simpleores_client.mixins.json"

    val versionRangeProp = stonecutter.properties.getOrNull<String>("version_range")
    val versionRange = if (versionRangeProp != null) {
        preToBeta(versionRangeProp)
    } else {
        ">=${preToBeta(stonecutter.properties.get<String>("min_version_range"))} <=${preToBeta(stonecutter.properties.get<String>("max_version_range"))}"
    }

    val expandProps = mapOf(
        "version" to version,
        "version_range" to versionRange,
        "fabricloader_version" to stonecutter.properties.get<String>("deps.fabricloader_version"),
        "bucketlib_expansion" to if (hasBucketlib) bucketlibExpansion else "",
        "aw_file" to accesswidener,
        "client_mixin_expansion" to if (hasBucketlib) "" else clientMixinExpansion,
        "compatibility_level" to "JAVA_${javaversion.ordinal + 1}",
        "fabric_api_breaks_version" to stonecutter.properties.get<String>("breaks.fabric_api")
    )

    // NeoForge-only files must not end up in the Fabric jar
    exclude("META-INF/neoforge.mods.toml", "META-INF/accesstransformer.cfg", "*.neoforge.mixins.json")

    filesMatching(listOf("fabric.mod.json", "*.mixins.json")) {
        expand(expandProps)
    }
    inputs.properties(expandProps)
}

sourceSets {
    all {
        // NeoForge code is only compiled by the NeoForge nodes
        java.exclude("**/neoforge/**")
    }

    main {
        resources {
            srcDirs(
                project.file("versions/${stonecutter.current.project}/src/main/generated"),
                project.file("versions/${stonecutter.current.project}/src/main/resources"),
                when {
                    hasBucketlib -> rootProject.file("sc-resources/main/hasbucketlib")
                    containsBucket -> rootProject.file("sc-resources/main/nobucketlib")
                    else -> rootProject.file("sc-resources/main/nobucket")
                }
            )
        }
    }

    get("client").resources {
        srcDirs(
            when {
                hasBucketlib -> rootProject.file("sc-resources/client/hasbucketlib")
                containsBucket -> rootProject.file("sc-resources/client/nobucketlib")
                else -> rootProject.file("sc-resources/client/nobucket")
            }
        )
    }
}

// Chest loot of the base mod: one template set, adapted to the version (folder name and copper equipment)
val chestLootDir = layout.buildDirectory.dir("generated/chest-loot")
val generateChestLoot by tasks.registering(Copy::class) {
    val singularFolder = stonecutter.eval(stonecutter.current.version, ">=1.21")
    val maxVersionRange = stonecutter.properties.getOrNull<String>("max_version_range") ?: "1.21.8"
    val vanillaCopperTools = isDeobf || stonecutter.eval(maxVersionRange, ">1.21.8")
    val copperTools = Regex("simpleores:copper_(axe|boots|chestplate|helmet|leggings|pickaxe|shovel|sword)")

    from(rootProject.file("sc-resources/chest-loot"))
    into(chestLootDir.map { it.dir("data/simpleores/${if (singularFolder) "loot_table" else "loot_tables"}/chest") })
    if (vanillaCopperTools) filter { line -> copperTools.replace(line, "minecraft:copper_$1") }
}
sourceSets.main { resources.srcDir(chestLootDir) }
tasks.processResources { dependsOn(generateChestLoot) }

// fabricApi is not statically accessible
val fabricApiExt = project.extensions.findByName("fabricApi")
if (fabricApiExt != null) {
    // Wrap it in the strict FabricApiExtension type from Loom
    configure<net.fabricmc.loom.api.fabricapi.FabricApiExtension> {
        configureDataGeneration {
            client = true
        }

        // The gametests need the Fabric Advancement API, which only exists from 26.2
        // Loom creates the "gametest" source set and "runGameTest", to run manually
        if (stonecutter.eval(stonecutter.current.version, ">=26.2")) {
            configureTests {
                createSourceSet = true
                modId = "simpleores-gametest"
                enableGameTests = true
                enableClientGameTests = false
                eula = true
            }

            // Loom hooks "runGameTest" to "check", remove it to not start a server on every build
            tasks.named("check") {
                setDependsOn(dependsOn.filterNot { dep ->
                    val name = when (dep) {
                        is Task -> dep.name
                        is TaskProvider<*> -> dep.name
                        else -> dep.toString()
                    }
                    name == "runGameTest"
                })
            }
        }
    }
}

val includesBucketlib = stonecutter.eval(stonecutter.current.version, "<=1.20.1") && hasBucketlib
val hasClothConfig: Boolean = hasBucketlib && stonecutter.properties.getOrNull<String>("deps.cloth_config") != null

// Real Mojang id of the target version, "deps.minecraft" wins over the Stonecutter folder name
// (the folder can be "26.3-deobf" while building against "26.3-snapshot-8")
val minecraftVersion: String = stonecutter.properties.getOrNull<String>("deps.minecraft")
    ?: stonecutter.current.version

// Derived from the Mojang id rather than the folder name, so "-snapshot" can be dropped from Stonecutter names
val isSnapshot = VersionRangeParser.isSnapshotId(minecraftVersion)

dependencies {
    add("minecraft", "com.mojang:minecraft:${minecraftVersion}")

    // loomx applies the official Mojang mappings when relevant, and does nothing otherwise
    loomx.applyMojangMappings()

    // 3. Maps modImplementation -> implementation when deobf
    fun dep(configuration: String, dependencyNotation: Any, action: ExternalModuleDependency.() -> Unit = {}) {
        val configName = if (isDeobf && configuration.startsWith("mod")) {
            configuration.substring(3).replaceFirstChar { it.lowercase() }
        } else {
            configuration
        }
        add(configName, dependencyNotation)?.apply {
            (this as? ExternalModuleDependency)?.action()
        }
    }

    depOrNull("fabric_loader")?.let {
        dep("modImplementation", "net.fabricmc:fabric-loader:$it")
    }

    depOrNull("fabric_api")?.let {
        dep("modImplementation", "net.fabricmc.fabric-api:fabric-api:$it")
    }

    depOrNull("midnightlib")?.let { midnightlibVersion ->
        val isLegacyMidnightLib = isMojmaps && midnightlibVersion.endsWith("-fabric") && !midnightlibVersion.contains("+")
        val midnightlib: String = if(isLegacyMidnightLib) {
            "maven.modrinth:midnightlib:$midnightlibVersion"
        } else {
            "eu.midnightdust:midnightlib:$midnightlibVersion"
        }

        dep("modImplementation", midnightlib) {
            exclude(group = "net.fabricmc.fabric-api")
            exclude(group = "com.terraformersmc", module = "modmenu")
        }
        dep("include", midnightlib) {
            exclude(group = "net.fabricmc.fabric-api")
            exclude(group = "com.terraformersmc", module = "modmenu")
        }
    }

    depOrNull("mod_menu")?.let {
        dep("modImplementation", "maven.modrinth:modmenu:$it")
    }

    depOrNull("bucketlib")?.let { bucketlibVersion ->
        depOrNull("cloth_config")?.let {
            dep("modApi", "me.shedaniel.cloth:cloth-config-fabric:$it") {
                exclude(group = "net.fabricmc.fabric-api")
            }
        }
        dep("modImplementation", "com.github.cech12.BucketLib:fabric:$bucketlibVersion") {
            exclude(group = "net.fabricmc.fabric-api")
        }

        if(includesBucketlib) {
            dep("include", "com.github.cech12.BucketLib:fabric:$bucketlibVersion") {
                exclude(group = "net.fabricmc.fabric-api")
            }
        }
    }
}

// A dependency missing for the current node in stonecutter.properties.toml means it is unused
fun depOrNull(depName: String): String? = stonecutter.properties.getOrNull<String>("deps.$depName")

tasks.withType<JavaCompile>().configureEach {
    options.release.set(javaversion.toString().toInt())
}

java {
    sourceCompatibility = javaversion
    targetCompatibility = javaversion
}

fun preToBeta(version: String?): String? {
    version ?: return null
    return version
        .replace(Regex("-rc(\\d+)"), "-rc.$1")
        .replace(Regex("-pre(\\d+)"), "-beta.$1")
}

fun runtimeVersionToSnapshot(versionProperty: String): String? {
    val version = project.findProperty(versionProperty) as? String ?: return null
    return version.replace(Regex("""(\d+\.\d+\.\d+)-alpha\.(\d+)\.(\d+)\.a""")) {
        "${it.groupValues[2]}w${it.groupValues[3]}a"
    }
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName.get()}" }
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
            artifactId = base.archivesName.get()
            version = project.version.toString()
            from(components["java"])
        }
    }
}

// The Actions GITHUB_TOKEN has a low shared quota, so releases use GH_RELEASE_TOKEN when provided
// An unset Actions secret is injected as an empty string, so blank values are ignored for the fallback
fun secret(name: String): String? =
    ((findProperty(name) as String?) ?: System.getenv(name))?.takeIf { it.isNotBlank() }

val githubToken = secret("GH_RELEASE_TOKEN") ?: secret("GITHUB_TOKEN")
val githubChangelog: String = try {
    NewGithubChangelog.getChangelog(project.rootDir.toPath(), githubToken)
} catch (e: Exception) {
    // If the GitHub API is down, use an empty string instead of failing the build
    project.logger.warn("Impossible de récupérer le changelog de GitHub : ${e.message}")
    "Changelog is currently unavailable."
}

val curseforgeToken =
    (findProperty("CURSEFORGE_TOKEN") as String?)
        ?: System.getenv("CURSEFORGE_TOKEN")
val modrinthToken =
    (findProperty("MODRINTH_TOKEN") as String?)
        ?: System.getenv("MODRINTH_TOKEN")

// Rebuilds the "gradle.properties" view VersionRangeParser expects from stonecutter.properties.toml
val versionRangeProperties: Map<String, String> = buildMap {
    stonecutter.properties.getOrNull<String>("version_range")?.let { put("version_range", it) }
    stonecutter.properties.getOrNull<String>("min_version_range")?.let { put("min_version_range", it) }
    stonecutter.properties.getOrNull<String>("max_version_range")?.let { put("max_version_range", it) }
}

// Based on the Mojang id rather than the folder name, so successive snapshots get distinct version numbers
fun formatPublishVersion(): String = "${stonecutter.properties.get<String>("mod.version")}-$minecraftVersion"

// CurseForge only lists one "<version>-snapshot" per cycle, so 26.3-snapshot-8 is published as "26.3-snapshot"
// "curseforge_versions" (comma-separated) overrides the list
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

// CI: -PprebuiltJarsDir=<dir> publishes already built jars without compiling
val prebuiltJar: File? = providers.gradleProperty("prebuiltJarsDir").orNull
    ?.let { File(it, "${base.archivesName.get()}-${project.version}.jar") }

publishMods {
    // ./gradlew publishMods -PpublishDryRun: generates the requests without sending anything
    dryRun = providers.gradleProperty("publishDryRun").isPresent

    file.set(prebuiltJar?.let { layout.file(provider { it }) } ?: loomx.modJar.flatMap { it.archiveFile })

    displayName.set("SimpleOres Fabric ${stonecutter.properties.get<String>("mod.version")} for $minecraftVersion")
    version.set(formatPublishVersion())
    changelog.set(githubChangelog)

    type.set(
        if (isSnapshot || !hasBucketlib) BETA
        else STABLE
    )

    modLoaders.addAll("fabric", "quilt")

    val versions = VersionRangeParser.parseVersionRange(versionRangeProperties)

    modrinth {
        projectId.set("Boe3chj8")
        accessToken.set(modrinthToken)

        minecraftVersions.addAll(versions)

        requires("fabric-api")

        optional("midnightlib")
        optional("modmenu")
        optional("energized-power")

        if (hasBucketlib) {
            if (includesBucketlib) {
                embeds("bucketlib")
            } else {
                requires("bucketlib")
            }
        }

        if (hasClothConfig) {
            requires("cloth-config")
        }
    }

    curseforge {
        projectId.set("1092987")
        accessToken.set(curseforgeToken)

        client.set(true)
        server.set(true)

        minecraftVersions.addAll(curseforgeVersions())

        requires("fabric-api")

        optional("midnightlib")
        optional("modmenu")
        optional("energized-power")

        if (hasBucketlib) {
            if (includesBucketlib) {
                embeds("bucketlib")
            } else {
                requires("bucketlib")
            }
        }

        if (hasClothConfig) {
            requires("cloth-config")
        }
    }
}
