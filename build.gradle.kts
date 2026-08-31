import net.paulem.buildscript.NewGithubChangelog
import net.paulem.buildscript.VersionRangeParser
import org.gradle.api.artifacts.ExternalModuleDependency

plugins {
    // loom-back-compat applique automatiquement le bon variant de Loom (Mojmaps ou mappings
    // officiels) selon la version : plus besoin de choisir/switcher entre fabric-loom et
    // fabric-loom-remap nous-mêmes.
    id("dev.kikugie.loom-back-compat")

    `maven-publish`
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"

    id("dev.kikugie.stonecutter")
}

// 1. Définition du contexte d'exécution (encore utile pour nos propres branches, indépendant
// du plugin Loom utilisé en interne par loom-back-compat)
val isDeobf = stonecutter.current.project.contains("deobf", ignoreCase = true)
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
val containsBucket = stonecutter.eval(stonecutter.current.project, ">1.19.4")

// 2. Interpolation conditionnelle du nom de l'Access Widener
val awSuffix = if (isDeobf) "-deobf.accesswidener" else ".accesswidener"
val accesswidener = when {
    hasBucketlib -> "hasbucketlib$awSuffix"
    containsBucket -> "nobucketlib$awSuffix"
    else -> "nobucket$awSuffix"
}

// Un seul variant de Loom étant appliqué (via loom-back-compat), plus besoin du contournement
// "configure<LoomGradleExtensionAPI>" : le bloc "loom" se résout directement.
loom {
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
val hasClothConfig: Boolean = hasBucketlib && stonecutter.properties.getOrNull<String>("deps.cloth_config") != null

// Identifiant Mojang réel de la version ciblée. "deps.minecraft" prime sur le nom du dossier
// Stonecutter : le dossier peut donc s'appeler "26.3-deobf" tout en compilant contre
// "26.3-snapshot-8". On utilise .version pour éviter que "-mojmaps"/"-deobf" ne s'infiltre ici.
val minecraftVersion: String = stonecutter.properties.getOrNull<String>("deps.minecraft")
    ?: stonecutter.current.version

// Déduit de l'identifiant Mojang (26.3-snapshot-8, 1.21.6-pre1, 25w14a…) et non du nom du
// dossier, ce qui permet de retirer le "-snapshot" des noms de versions Stonecutter.
val isSnapshot = VersionRangeParser.isSnapshotId(minecraftVersion)

dependencies {
    add("minecraft", "com.mojang:minecraft:${minecraftVersion}")

    // loomx applique les mappings Mojang officiels quand c'est pertinent pour la version
    // ciblée, et ne fait rien sinon : plus besoin de le déterminer nous-mêmes.
    loomx.applyMojangMappings()

    // 3. Fonction locale pour traduire modImplementation -> implementation si deobf
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

// Une dépendance absente du nœud courant dans stonecutter.properties.toml signifie qu'elle
// n'est pas utilisée pour cette version (remplace l'ancien sentinel "[VERSIONED]").
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

// Le GITHUB_TOKEN d'Actions est un jeton d'installation : son quota (partagé par dépôt et par
// heure) est bien plus vite épuisé que celui d'un PAT utilisateur. La création des releases passe
// donc par GH_RELEASE_TOKEN quand il est fourni, et retombe sur GITHUB_TOKEN sinon.
// Un secret Actions non défini est injecté comme chaîne vide, pas comme variable absente :
// on ignore donc explicitement les valeurs vides pour que le repli fonctionne.
fun secret(name: String): String? =
    ((findProperty(name) as String?) ?: System.getenv(name))?.takeIf { it.isNotBlank() }

val githubToken = secret("GH_RELEASE_TOKEN") ?: secret("GITHUB_TOKEN")
val githubChangelog: String = try {
    NewGithubChangelog.getChangelog(project.rootDir.toPath(), githubToken)
} catch (e: Exception) {
    // Si l'API GitHub est en panne, on utilise une chaîne vide au lieu de faire échouer le build
    project.logger.warn("Impossible de récupérer le changelog de GitHub : ${e.message}")
    "Changelog is currently unavailable."
}

val curseforgeToken =
    (findProperty("CURSEFORGE_TOKEN") as String?)
        ?: System.getenv("CURSEFORGE_TOKEN")
val modrinthToken =
    (findProperty("MODRINTH_TOKEN") as String?)
        ?: System.getenv("MODRINTH_TOKEN")

// Reconstitue la vue "gradle.properties" qu'attend VersionRangeParser à partir des propriétés
// centralisées de stonecutter.properties.toml (version_range/min/max ne sont plus exposées
// comme propriétés Gradle classiques).
val versionRangeProperties: Map<String, String> = buildMap {
    stonecutter.properties.getOrNull<String>("version_range")?.let { put("version_range", it) }
    stonecutter.properties.getOrNull<String>("min_version_range")?.let { put("min_version_range", it) }
    stonecutter.properties.getOrNull<String>("max_version_range")?.let { put("max_version_range", it) }
}

// Basé sur l'identifiant Mojang plutôt que sur le nom du dossier : deux snapshots successifs
// (26.3-snapshot-7 et -8) produisent ainsi des numéros de version distincts, exigés par
// Modrinth et CurseForge.
fun formatPublishVersion(): String = "${stonecutter.properties.get<String>("mod.version")}-$minecraftVersion"

// Versions de jeu déclarées sur CurseForge. CurseForge ne référence pas chaque snapshot
// individuellement mais un unique "<version>-snapshot" par cycle : 26.3-snapshot-8 est donc
// publié sous "26.3-snapshot". La propriété "curseforge_versions" (liste séparée par des
// virgules) permet de forcer la liste si CurseForge nomme la version autrement.
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

publishMods {
    file.set(loomx.modJar.flatMap { it.archiveFile })

    displayName.set("SimpleOres Fabric ${stonecutter.properties.get<String>("mod.version")} for $minecraftVersion")
    version.set(formatPublishVersion())
    changelog.set(githubChangelog)

    type.set(
        // Une version compilée contre un snapshot ne peut pas être marquée stable.
        if (isSnapshot || !hasBucketlib) BETA
        else STABLE
    )

    modLoaders.addAll("fabric", "quilt")

    val versions = VersionRangeParser.parseVersionRange(versionRangeProperties)

    github {
        accessToken.set(githubToken)
        repository.set("Paulem79/SimpleOres-fabric")
        commitish.set("stonecutter") // This is the branch the release tag will be created from
    }

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
