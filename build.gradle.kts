import net.paulem.buildscript.NewGithubChangelog
import net.paulem.buildscript.VersionRangeParser
import org.gradle.api.artifacts.ExternalModuleDependency
import net.fabricmc.loom.task.RemapJarTask

plugins {
    // Déclaration des plugins Loom sans les appliquer immédiatement (technique YACL)
    id("net.fabricmc.fabric-loom") version "1.17-SNAPSHOT" apply false
    id("net.fabricmc.fabric-loom-remap") version "1.17-SNAPSHOT" apply false

    `maven-publish`
    id("me.modmuss50.mod-publish-plugin") version "2.1.1"

    id("dev.kikugie.stonecutter")
}

// 1. Définition du contexte d'exécution
val isDeobf = stonecutter.current.project.contains("deobf", ignoreCase = true)
val isMojmaps = !isDeobf

// Application dynamique du plugin selon le contexte
if (isDeobf) {
    apply(plugin = "net.fabricmc.fabric-loom")
} else {
    apply(plugin = "net.fabricmc.fabric-loom-remap")
}

version = "${project.property("mod.version")}-${stonecutter.current.project}"
group = project.property("maven_group") as String

base {
    archivesName.set(project.property("mod.id") as String)
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

val hasBucketlib: Boolean = findProperty("deps.bucketlib")?.takeIf { it != "[VERSIONED]" } != null
val containsBucket = stonecutter.eval(stonecutter.current.project, ">1.19.4")

// 2. Interpolation conditionnelle du nom de l'Access Widener
val awSuffix = if (isDeobf) "-deobf.accesswidener" else ".accesswidener"
val accesswidener = when {
    hasBucketlib -> "hasbucketlib$awSuffix"
    containsBucket -> "nobucketlib$awSuffix"
    else -> "nobucket$awSuffix"
}

// Utilisation de configure pour garder le typage sûr après une application de plugin dynamique
configure<net.fabricmc.loom.api.LoomGradleExtensionAPI> {
    splitEnvironmentSourceSets()

    mods {
        register("simpleores") {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.getByName("client"))
        }
    }

    runConfigs.all {
        isIdeConfigGenerated = true // Syntaxe de propriété Kotlin DSL
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

    val versionRange = if(project.hasProperty("version_range")) {
        preToBeta("version_range")
    } else {
        ">=${preToBeta("min_version_range")} <=${preToBeta("max_version_range")}"
    }

    val expandProps = mapOf(
        "version" to version,
        "version_range" to versionRange,
        "fabricloader_version" to project.property("deps.fabricloader_version") as String,
        "bucketlib_expansion" to if (hasBucketlib) bucketlibExpansion else "",
        "aw_file" to accesswidener,
        "client_mixin_expansion" to if (hasBucketlib) "" else clientMixinExpansion,
        "compatibility_level" to "JAVA_${javaversion.ordinal + 1}",
        "fabric_api_breaks_version" to project.property("breaks.fabric_api") as String
    )

    filesMatching(listOf("fabric.mod.json", "*.mixins.json")) {
        expand(expandProps)
    }
    inputs.properties(expandProps)
}

sourceSets {
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

// Contournement dynamique pour le bloc fabricApi (inaccessible statiquement)
val fabricApiExt = project.extensions.findByName("fabricApi")
if (fabricApiExt != null) {
    // On encapsule dans le type strict FabricApiExtension fourni par Loom.
    // Le compilateur Kotlin DSL retrouvera ses petits et autorisera "client = true" !
    configure<net.fabricmc.loom.api.fabricapi.FabricApiExtension> {
        configureDataGeneration {
            client = true
        }
    }
}

val includesBucketlib = stonecutter.eval(stonecutter.current.version, "<=1.20.1") && hasBucketlib
// On utilise .version pour éviter que le "-mojmaps" ou "-deobf" ne s'infiltre ici
val isSnapshot = stonecutter.current.version.contains("snapshot", true)
val minecraftVersion = if(isSnapshot || (findProperty("deps.minecraft") != null && findProperty("deps.minecraft") != "[VERSIONED]")) {
    property("deps.minecraft")
} else {
    stonecutter.current.version // <-- Correction ici !
}

dependencies {
    add("minecraft", "com.mojang:minecraft:${minecraftVersion}")

    // Résolution des mappings uniquement sur Mojmaps et versions compatibles
    if(isMojmaps && stonecutter.eval(minecraftVersion.toString(), "<=1.21.11")) {
        val loomExt = project.extensions.getByName("loom") as net.fabricmc.loom.api.LoomGradleExtensionAPI
        // Utilisation de add() au lieu de l'accesseur dynamique mappings(...)
        add("mappings", loomExt.officialMojangMappings())
    }

    // 3. Fonction locale pour traduire modImplementation -> implementation si deobf
    fun dep(configuration: String, dependencyNotation: String, action: ExternalModuleDependency.() -> Unit = {}) {
        val configName = if (isDeobf && configuration.startsWith("mod")) {
            configuration.substring(3).replaceFirstChar { it.lowercase() }
        } else {
            configuration
        }
        add(configName, dependencyNotation)?.apply {
            (this as ExternalModuleDependency).action()
        }
    }

    if(checkSpecified("fabric_loader"))
        dep("modImplementation", "net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")

    if(checkSpecified("fabric_api"))
        dep("modImplementation", "net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

    if(checkSpecified("midnightlib")) {
        val isLegacyMidnightLib = isMojmaps && property("deps.midnightlib").toString().endsWith("-fabric") && !property("deps.midnightlib").toString().contains("+")
        val midnightlib: String = if(isLegacyMidnightLib) {
            "maven.modrinth:midnightlib:${property("deps.midnightlib")}"
        } else {
            "eu.midnightdust:midnightlib:${property("deps.midnightlib")}"
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

    if(checkSpecified("mod_menu"))
        dep("modImplementation", "maven.modrinth:modmenu:${property("deps.mod_menu")}")

    if(checkSpecified("bucketlib")) {
        if(checkSpecified("cloth_config")) {
            dep("modApi", "me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth_config")}") {
                exclude(group = "net.fabricmc.fabric-api")
            }
        }
        dep("modImplementation", "com.github.cech12.BucketLib:fabric:${property("deps.bucketlib")}") {
            exclude(group = "net.fabricmc.fabric-api")
        }

        if(includesBucketlib) {
            dep("include", "com.github.cech12.BucketLib:fabric:${property("deps.bucketlib")}") {
                exclude(group = "net.fabricmc.fabric-api")
            }
        }
    }
}

fun checkSpecified(depName: String): Boolean {
    val property = findProperty("deps.$depName")
    return property != null && property != "[VERSIONED]"
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(javaversion.toString().toInt())
}

java {
    sourceCompatibility = javaversion
    targetCompatibility = javaversion
}

fun preToBeta(versionProperty: String): String? {
    val version = project.findProperty(versionProperty) as? String ?: return null
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

val githubTokenName = "GITHUB_COMMIT_TOKEN"
val githubChangelog: String = NewGithubChangelog.getChangelog(project.rootDir.toPath(), System.getenv(githubTokenName) ?: (project.findProperty(githubTokenName) as String?))

val curseforgeToken =
    (findProperty("CURSEFORGE_TOKEN") as String?)
        ?: System.getenv("CURSEFORGE_TOKEN")
val modrinthToken =
    (findProperty("MODRINTH_TOKEN") as String?)
        ?: System.getenv("MODRINTH_TOKEN")

fun formatPublishVersion(): String {
    val subs = project.version.toString().split('-')
    return subs[0] + "-" + subs[1]
}

publishMods {
    file.set(
        if (isDeobf) {
            tasks.named<Jar>("jar").flatMap { it.archiveFile }
        } else {
            tasks.named<RemapJarTask>("remapJar").flatMap { it.archiveFile }
        }
    )

    displayName.set("SimpleOres Fabric ${project.property("mod.version")}")
    version.set(formatPublishVersion())
    changelog.set(githubChangelog)

    type.set(
        if (!hasBucketlib) BETA
        else STABLE
    )

    modLoaders.addAll("fabric", "quilt")

    val versions = VersionRangeParser.parseVersionRange(project.properties)
    //TODO: Implement github publish: https://modmuss50.github.io/mod-publish-plugin/platforms/github/

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
    }

    curseforge {
        projectId.set("1092987")
        accessToken.set(curseforgeToken)

        client.set(true)
        server.set(true)

        minecraftVersions.addAll(
            if (isSnapshot) {
                listOf(stonecutter.current.project)
            } else {
                VersionRangeParser.parseVersionRange(
                    project.properties,
                    VersionRangeParser.CompiledVersions.VersionType.RELEASE
                )
            }
        )

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
    }
}