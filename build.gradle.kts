import ovh.paulem.buildscript.NewGithubChangelog
import ovh.paulem.buildscript.VersionRangeParser

buildscript {
	repositories {
		maven { url = uri("https://maven.paulem.ovh/releases") }
	}
	dependencies {
		classpath("ovh.paulem:simpleores-buildscript:1.0.0")
	}
}

plugins {
	id("fabric-loom") version "1.11-SNAPSHOT"

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
	maven("https://maven.nucleoid.xyz/") { name = "Nucleoid" }
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
		runDir = "run" // Use a shared run folder and create separate worlds
	}
}

sourceSets {
	main {
		resources {
			srcDirs(
				project.file("versions/${stonecutter.current.project}/src/main/generated"),
				project.file("versions/${stonecutter.current.project}/src/main/resources")
			)
		}
	}
}

fabricApi {
	configureDataGeneration {
		client = true
	}
}

tasks.register<Copy>("distJar") {
	group = "build"
	dependsOn(tasks.build)
	val projectName = project.base.archivesName.get()
	val versionName = project.version.toString()
	val jarFile = file("build/libs/${projectName}-${versionName}.jar")
	if (!jarFile.exists()) {
		throw GradleException("Jar file $jarFile does not exist. Please build the project first.")
	}
	from(jarFile)
	doFirst {
		file("$rootDir/dist").mkdirs()
	}
	into("$rootDir/dist")
}

dependencies {
	minecraft("com.mojang:minecraft:${stonecutter.current.project}")
	if(checkSpecified("yarn_mappings"))
		mappings("net.fabricmc:yarn:${property("deps.yarn_mappings")}:v2")
	if(checkSpecified("fabric_loader"))
		modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
	if(checkSpecified("fabric_api"))
		modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

	if(checkSpecified("cloth_config"))
		modApi("me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth_config")}") {
			exclude(group = "net.fabricmc.fabric-api")
		}
	if(checkSpecified("mod_menu"))
		modImplementation("com.terraformersmc:modmenu:${property("deps.mod_menu")}")

	if(checkSpecified("bucketlib"))
		modImplementation("com.github.cech12.BucketLib:fabric:${property("deps.bucketlib")}")
}

fun checkSpecified(depName: String): Boolean {
	val property = findProperty("deps.$depName");
	return property != null && property != "[VERSIONED]"
}

stonecutter {
	constants.put("hasBucketlib", !listOf(null, "[VERSIONED]").contains(findProperty("deps.bucketlib")))

	swaps["armorType"] = when {
		eval(current.version, "<=1.21") -> "net.minecraft.item.ArmorItem.Type"
		else -> "net.minecraft.item.equipment.EquipmentType"
	}

	swaps["armorRegistry"] = when {
		eval(current.version, "=1.21") -> "net.minecraft.registry.entry.RegistryEntry<net.minecraft.item.ArmorMaterial>"
		else -> "net.minecraft.item.equipment.ArmorMaterial"
	}

	swaps["tagOrIngredient"] = when {
		eval(current.version, "<=1.21") -> "java.util.function.Supplier<net.minecraft.recipe.Ingredient>"
		else -> "net.minecraft.registry.tag.TagKey<net.minecraft.item.Item>"
	}

	swaps["generatorOrExporter"] = when {
		eval(current.version, ">=1.21.3") -> "net.minecraft.data.recipe.RecipeGenerator"
		eval(current.version, ">1.20.1") -> "net.minecraft.data.server.recipe.RecipeExporter"
		else -> "java.util.function.Consumer<net.minecraft.data.server.recipe.RecipeJsonProvider>"
	}

	swaps["advancementEntry"] = when {
		eval(current.version, "=1.20.1") -> "net.minecraft.advancement.Advancement"
		else -> "net.minecraft.advancement.AdvancementEntry"
	}

	replacements {
		string {
			direction = eval(current.version, "<=1.21.3")
			replace("net.minecraft.client.data", "net.minecraft.data.client")

			phase = "FIRST"
		}

		string {
			direction = eval(current.version, "<=1.21.3")
			replace("net.minecraft.data.recipe", "net.minecraft.data.server.recipe")

			phase = "FIRST"
		}

		for (cls in listOf("TagRegistration")) {
			string {
				direction = eval(node.metadata.version, "<=1.20.4")
				phase = "FIRST"
				replace(
					"net.fabricmc.fabric.impl.tag.convention.v2.$cls;",
					"net.fabricmc.fabric.impl.tag.convention.$cls;"
				)
			}
		}

		for (cls in listOf("ConventionalItemTags", "ConventionalBlockTags")) {
			string {
				direction = eval(node.metadata.version, "<=1.20.4")
				phase = "FIRST"
				replace(
					"net.fabricmc.fabric.api.tag.convention.v2.$cls;",
					"net.fabricmc.fabric.api.tag.convention.v1.$cls;"
				)
			}
		}

		for (cls in listOf("FabricModelProvider")) {
			string {
				direction = eval(node.metadata.version, ">=1.21.5")
				phase = "FIRST"
				replace(
					"import net.fabricmc.fabric.api.datagen.v1.provider.$cls;",
					"import net.fabricmc.fabric.api.client.datagen.v1.provider.$cls;"
				)
			}
		}

		string {
			direction = eval(node.metadata.version, "<=1.21")
			phase = "FIRST"
			replace(
				"import net.minecraft.item.equipment.ArmorMaterial;",
				"import net.minecraft.item.ArmorMaterial;"
			)
		}

		string {
			direction = eval(node.metadata.version, "=1.20.1")
			phase = "FIRST"
			replace(
				"RecipeExporter ",
				"java.util.function.Consumer<net.minecraft.data.server.recipe.RecipeJsonProvider> "
			)
		}

		string {
			direction = eval(node.metadata.version, "<=1.20.1")
			phase = "FIRST"
			replace(
				"de.cech12.bucketlib",
				"com.github.cech12.BucketLib"
			)
		}
	}
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
		gameVersions = VersionRangeParser.parseVersionRange(
			project.property("min_version_range") as String,
			project.property("max_version_range") as String
		)
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

				gameVersions = VersionRangeParser.parseVersionRange(
					project.property("min_version_range") as String,
					project.property("max_version_range") as String,
					VersionRangeParser.CompiledVersions.VersionType.RELEASE
				)
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
