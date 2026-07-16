import net.paulem.buildscript.NewGithubChangelog
import net.paulem.buildscript.VersionRangeParser

plugins {
	id("net.fabricmc.fabric-loom-remap") version "1.17-SNAPSHOT"

	`maven-publish`
	id("me.shedaniel.unified-publishing") version "0.1.+"

	id("dev.kikugie.stonecutter")
}

version = "${project.property("mod.version")}-${stonecutter.current.project}"
group = project.property("maven_group") as String

base {
	archivesName.set(project.property("mod.id") as String)
}

repositories {
	mavenCentral()
	maven { url = uri("https://jitpack.io") }
	maven { url = uri("https://maven.shedaniel.me/") }
	maven {
		name = "Terraformers"
		url = uri("https://maven.terraformersmc.com/")
	}
	maven { url = uri("https://maven.terraformersmc.com/releases/") }
	maven { url = uri("https://maven.architectury.dev/") }
	maven {
		name = "paulemReleases"
		url = uri("https://maven.paulem.net/releases")
	}
	maven("https://maven.nucleoid.xyz/") { name = "Nucleoid" }
	maven("https://maven.midnightdust.eu/releases")
	maven("https://api.modrinth.com/maven")
	mavenLocal()
}

// If this version has BucketLib
val hasBucketlib: Boolean = findProperty("deps.bucketlib")?.takeIf { it != "[VERSIONED]" } != null
// If this version has the buckets
val containsBucket = stonecutter.eval(stonecutter.current.project, ">1.19.4")

val accesswidener = when {
	hasBucketlib -> "hasbucketlib.accesswidener"
	containsBucket -> "nobucketlib.accesswidener"
	else -> "nobucket.accesswidener"
}

loom {
	splitEnvironmentSourceSets()

	mods {
		register("simpleores") {
			sourceSet(sourceSets.main.get())
			sourceSet(sourceSets.getByName("client"))
		}
	}

	runConfigs.all {
		ideConfigGenerated(true) // Run configurations are not created for subprojects by default
		runDir = "run" // Use a shared run folder and create separate worlds
	}

	accessWidenerPath = project.rootProject.file("src/main/resources/accesswideners/$accesswidener")
}

tasks.processResources {
	duplicatesStrategy = DuplicatesStrategy.INCLUDE

	val bucketlibExpansion = "\", \"bucketlib\": \"*"
	val clientMixinExpansion = ""//"\", \"simpleores_client.mixins.json";

	// Check has property version_range
	val versionRange = if(project.hasProperty("version_range")) {
		preToBeta("version_range")
	} else {
		// Compute from old values
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
					hasBucketlib -> {
						rootProject.file("sc-resources/main/hasbucketlib")
					}
					containsBucket -> {
						rootProject.file("sc-resources/main/nobucketlib")
					}
					else -> {
						rootProject.file("sc-resources/main/nobucket")
					}
				}
			)
		}
	}

	get("client").resources {
		srcDirs(
			when {
				hasBucketlib -> {
					rootProject.file("sc-resources/client/hasbucketlib")
				}
				containsBucket -> {
					rootProject.file("sc-resources/client/nobucketlib")
				}
				else -> {
					rootProject.file("sc-resources/client/nobucket")
				}
			}
		)
	}
}

fabricApi {
	configureDataGeneration {
		client = true
	}
}

val includesBucketlib = stonecutter.eval(stonecutter.current.version, "<=1.20.1") && hasBucketlib

val isSnapshot = stonecutter.current.project.contains("snapshot", true)
val minecraftVersion = if(isSnapshot ||
	(findProperty("deps.minecraft") != null && findProperty("deps.minecraft") != "[VERSIONED]")) property("deps.minecraft")
else stonecutter.current.project

dependencies {
	minecraft("com.mojang:minecraft:${minecraftVersion}")

	// Before or in 1.21.11
	if(stonecutter.eval(minecraftVersion.toString(), "<=1.21.11")) {
		mappings(loom.officialMojangMappings())
	}
	if(checkSpecified("fabric_loader"))
		modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
	if(checkSpecified("fabric_api"))
		modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

	if(checkSpecified("midnightlib")) {
		// TODO: Might be needed in deobf in the future
		// New : 1.9.2+26.1-fabric, legacy : 1.3.0-fabric, check the notation to determine if legacy
		val isLegacyMidnightLib = property("deps.midnightlib").toString().endsWith("-fabric") && !property("deps.midnightlib").toString().contains("+")
		val midnightlib: String = if(isLegacyMidnightLib) {
			"maven.modrinth:midnightlib:${property("deps.midnightlib")}"
		} else {
			"eu.midnightdust:midnightlib:${property("deps.midnightlib")}"
		}

		modImplementation(midnightlib) {
			exclude(group = "net.fabricmc.fabric-api")
		}
		include(midnightlib) {
			exclude(group = "net.fabricmc.fabric-api")
		}
	}
	if(checkSpecified("mod_menu"))
		modImplementation("com.terraformersmc:modmenu:${property("deps.mod_menu")}")

	if(checkSpecified("bucketlib")) {
		// cloth config required
		if(checkSpecified("cloth_config")) {
			modApi("me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth_config")}") {
				exclude(group = "net.fabricmc.fabric-api")
			}
		}
		modImplementation("com.github.cech12.BucketLib:fabric:${property("deps.bucketlib")}") {
			exclude(group = "net.fabricmc.fabric-api")
		}

		if(includesBucketlib)
			include("com.github.cech12.BucketLib:fabric:${property("deps.bucketlib")}") {
				exclude(group = "net.fabricmc.fabric-api")
			}
	}
}

fun checkSpecified(depName: String): Boolean {
	val property = findProperty("deps.$depName")
	return property != null && property != "[VERSIONED]"
}

val javaversion = if (stonecutter.eval(stonecutter.current.version, ">=26.1"))
	JavaVersion.VERSION_25 else if (stonecutter.eval(stonecutter.current.version, ">=1.20.5"))
	JavaVersion.VERSION_21 else JavaVersion.VERSION_17

tasks.withType<JavaCompile>().configureEach {
	options.release.set(javaversion.toString().toInt())
}

java {
	sourceCompatibility = javaversion
	targetCompatibility = javaversion
}

fun preToBeta(versionProperty: String): String? {
	val version = project.property(versionProperty) as String? ?: return null

	return version
		.replace(Regex("-rc(\\d+)"), "-rc.$1")
		.replace(Regex("-pre(\\d+)"), "-beta.$1")
}

fun runtimeVersionToSnapshot(versionProperty: String): String? {
	val version = project.property(versionProperty) as String? ?: return null

	// 1.21.9-alpha.25.31.a -> 25w31a
	return version.replace(Regex("""(\d+\.\d+\.\d+)-alpha\.(\d+)\.(\d+)\.a""")) {
		"${it.groupValues[2]}w${it.groupValues[3]}a"
	}
}

tasks.jar {
	from("LICENSE") {
		rename { "${it}_${project.base.archivesName.get()}" }
	}
}

// ------------------------ PUBLISH MODS ------------------------
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

val projectName = project.base.archivesName.get()
val versionName = project.version.toString()
val distFileName = "${projectName}-${versionName}.jar"

tasks.register<Copy>("distJar") {
	group = "build"
	dependsOn(tasks.build)
	val jarFile = file("build/libs/${distFileName}")
	if (!jarFile.exists()) {
		println("Jar file $jarFile does not exist. Please build the project first.")
	}
	from(jarFile)
	doFirst {
		file("$rootDir/dist").mkdirs()
	}
	into("$rootDir/dist")
}

tasks.publishUnified {
	dependsOn(tasks.getByName("distJar")) // Ensure the jar is built before publishing
}

unifiedPublishing {
	project {
		displayName = "SimpleOres Fabric ${project.property("mod.version")}" // Optional, name of the file
		version = project.version.toString() // Optional, Inferred from project by default
		changelog = githubChangelog // Optional, in markdown format
		releaseType = if(!hasBucketlib) "beta" else "release" // Optional, use "release", "beta" or "alpha"

		gameVersions = VersionRangeParser.parseVersionRange(project.properties)
		gameLoaders = listOf("fabric", "quilt")

		mainPublication.set(project.rootDir.toPath().resolve("dist").resolve(distFileName).toFile()) // Declares the publicated jar

		relations {
			depends {
				modrinth = "fabric-api"
				curseforge = "fabric-api"
			}
			optional {
				modrinth = "midnightlib"
				curseforge = "midnightlib"
			}
			optional {
				modrinth = "modmenu"
				curseforge = "modmenu"
			}
			optional {
				modrinth = "energized-power"
				curseforge = "energized-power"
			}

			if(hasBucketlib) {
				if(includesBucketlib) {
					includes {
						modrinth = "bucketlib"
						curseforge = "bucketlib"
					}
				} else {
					depends {
						modrinth = "bucketlib"
						curseforge = "bucketlib"
					}
				}
			}
		}

		val curseforgeToken = (project.findProperty("CURSEFORGE_TOKEN") ?: System.getenv("CURSEFORGE_TOKEN")) as String?
		if (curseforgeToken != null) { // No pre or rc on curseforge
			curseforge {
				token = curseforgeToken
				id = "1092987" // Required, must be a string, ID of CurseForge project

				gameVersions = if(isSnapshot) {
					listOf(stonecutter.current.project)
				} else {
					VersionRangeParser.parseVersionRange(
						project.properties,
						VersionRangeParser.CompiledVersions.VersionType.RELEASE
					)
				}
			}
		}

		val modrinthToken = (project.findProperty("MODRINTH_TOKEN") ?: System.getenv("MODRINTH_TOKEN")) as String?
		if (modrinthToken != null) {
			modrinth {
				token = modrinthToken
				id = "Boe3chj8" // Required, must be a string, ID of Modrinth project
			}
		}
	}
}
