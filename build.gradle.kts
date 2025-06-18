import ovh.paulem.buildscript.*

buildscript {
	repositories {
		maven { url = uri("https://maven.paulem.ovh/releases") }
	}
	dependencies {
		classpath("ovh.paulem:simpleores-buildscript:1.0.0")
	}
}

plugins {
	id("fabric-loom") version "1.10-SNAPSHOT"

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
		url = uri("https://maven.paulem.ovh/releases")
	}
	mavenLocal()
}

loom {
	splitEnvironmentSourceSets()

	mods {
		create("simpleores") {
			sourceSet(sourceSets.main.get())
			sourceSet(sourceSets["client"])
		}
	}

	runConfigs.all {
		ideConfigGenerated(true) // Run configurations are not created for subprojects by default
		runDir = "../../run" // Use a shared run folder and create separate worlds
	}
}

sourceSets {
	main {
		resources {
			srcDir(project.file("versions/${stonecutter.current.project}/src/main/generated"))
		}
	}
}

fabricApi {
	configureDataGeneration {
		client = true
	}
}

dependencies {
	minecraft("com.mojang:minecraft:${stonecutter.current.project}")
	mappings("net.fabricmc:yarn:${property("deps.yarn_mappings")}:v2")
	modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
	modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

	modApi("me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth_config")}") {
		exclude(group = "net.fabricmc.fabric-api")
	}
	modImplementation("com.terraformersmc:modmenu:${property("deps.mod_menu")}")
}

tasks.processResources {
	val expandProps = mapOf(
		"version" to version,
		"min_version_range" to preToBeta("min_version_range"),
		"max_version_range" to preToBeta("max_version_range"),
	)

	filesMatching(listOf("fabric.mod.json", "*.mixins.json")) {
		expand(expandProps)
	}
	inputs.properties(expandProps)
}

fun preToBeta(versionProperty: String): String? {
	val version = project.property(versionProperty) as String?

	if (version == null) return null
	return version
		.replace(Regex("-rc(\\d+)"), "-rc.$1")
		.replace(Regex("-pre(\\d+)"), "-beta.$1")
}

val javaversion = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5"))
	JavaVersion.VERSION_21 else JavaVersion.VERSION_17

tasks.withType<JavaCompile>().configureEach {
	options.release.set(javaversion.toString().toInt())
}

java {
	withSourcesJar()

	sourceCompatibility = javaversion
	targetCompatibility = javaversion
}

tasks.jar {
	from("LICENSE") {
		rename { "${it}_${project.base.archivesName.get()}" }
	}
}

// ------------------------ PUBLISH MODS ------------------------
unifiedPublishing {
	val stillBeta = stonecutter.eval(stonecutter.current.version, ">1.21.3")

	project {
		displayName = "SimpleOres Refabricated ${project.property("mod.version")}" // Optional, name of the file
		version = project.version.toString() // Optional, Inferred from project by default
		changelog = if(stillBeta) {
			"**This version does not include the copper bucket, as BucketLib has not yet been updated!**\n\n" + NewGithubChangelog.getChangelog()
		} else {
			NewGithubChangelog.getChangelog()
		} // Optional, in markdown format
		releaseType = if(stillBeta) "beta" else "release" // Optional, use "release", "beta" or "alpha"
		gameVersions = VersionRangeParser.parseVersionRange(project.property("min_version_range") as String, project.property("max_version_range") as String)
		gameLoaders = listOf("fabric", "quilt")

		mainPublication.set(tasks.remapJar.get().archiveFile) // Declares the publicated jar

		relations {
			depends {
				modrinth = "fabric-api"
				curseforge = "fabric-api"
			}
			depends {
				modrinth = "cloth-config"
				curseforge = "cloth-config"
			}
			optional {
				modrinth = "modmenu"
				curseforge = "modmenu"
			}
			optional {
				modrinth = "energized-power"
				curseforge = "energized-power"
			}

			if(stillBeta) {
				depends {
					modrinth = "bucketlib"
					curseforge = "bucketlib"
				}
			}
		}

		val curseforgeToken = (project.findProperty("CURSEFORGE_TOKEN") ?: System.getenv("CURSEFORGE_TOKEN")) as String?
		if (curseforgeToken != null) { // No pre or rc on curseforge
			curseforge {
				token = curseforgeToken
				id = "1092987" // Required, must be a string, ID of CurseForge project

				gameVersions = VersionRangeParser.parseVersionRange(project.property("min_version_range") as String, project.property("max_version_range") as String
				, VersionRangeParser.CompiledVersions.VersionType.RELEASE)
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

