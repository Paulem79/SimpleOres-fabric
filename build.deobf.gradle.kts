import net.paulem.buildscript.NewGithubChangelog
import net.paulem.buildscript.VersionRangeParser

buildscript {
	repositories {
		maven { url = uri("https://maven.paulem.net/releases") }
	}
}

plugins {
    id("net.fabricmc.fabric-loom") version "1.14-SNAPSHOT"

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
	mavenLocal()
}

// If this version has BucketLib
val hasBucketlib: Boolean = findProperty("deps.bucketlib")?.takeIf { it != "[VERSIONED]" } != null
// If this version has BucketLib
val hasClothConfig: Boolean = findProperty("deps.cloth_config")?.takeIf { it != "[VERSIONED]" } != null
// If this version has the buckets
val containsBucket = stonecutter.eval(stonecutter.current.project, ">1.19.4")

val accesswidener = when {
    //hasBucketlib -> "hasbucketlib-deobf.accesswidener"
    containsBucket -> "nobucketlib-deobf.accesswidener"
    else -> "nobucket-deobf.accesswidener"
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
    val clientMixinExpansion = "\", \"simpleores_client.mixins.json"

    val expandProps = mapOf(
        "version" to version,
        "min_version_range" to preToBeta("min_version_range"),
        "max_version_range" to preToBeta("max_version_range"),
        "fabricloader_version" to project.property("deps.fabricloader_version") as String,
        "bucketlib_expansion" to if (hasBucketlib) bucketlibExpansion else "",
        "aw_file" to accesswidener,
        "client_mixin_expansion" to if (hasBucketlib) "" else clientMixinExpansion,

        "compatibility_level" to "JAVA_${javaversion.ordinal + 1}",
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

stonecutter {
    constants.put("hasBucketlib", hasBucketlib)
	constants.put("hasClothConfig", hasClothConfig)
    dependencies.put("maxVersionRange", property("max_version_range") as String)
    constants.put("hasCopperTools", stonecutter.eval(property("max_version_range") as String, ">1.21.8"))
    constants.put("containsBucket", containsBucket)

	swaps["armorType"] = when {
		eval(current.version, "<=1.21") -> "net.minecraft.world.item.ArmorItem.Type"
		else -> "net.minecraft.world.item.equipment.ArmorType"
	}

	swaps["armorRegistry"] = when {
		eval(current.version, "=1.21") -> "net.minecraft.core.Holder<net.minecraft.world.item.ArmorMaterial>"
		else -> "net.minecraft.world.item.equipment.ArmorMaterial"
	}

	swaps["tagOrIngredient"] = when {
		eval(current.version, "<=1.21") -> "java.util.function.Supplier<net.minecraft.world.item.crafting.Ingredient>"
		else -> "net.minecraft.tags.TagKey<net.minecraft.world.item.Item>"
	}

	swaps["generatorOrExporter"] = when {
        eval(current.version, ">=1.21.3") -> "net.minecraft.data.recipes.RecipeProvider"
		eval(current.version, ">1.20.1") -> "net.minecraft.data.recipes.RecipeOutput"
		else -> "java.util.function.Consumer<net.minecraft.data.recipes.FinishedRecipe>"
	}

	swaps["advancementEntry"] = when {
		eval(current.version, "<=1.20.1") -> "net.minecraft.advancements.Advancement"
		else -> "net.minecraft.advancements.AdvancementHolder"
	}

    swaps["location"] = when {
        eval(current.version, ">1.21.10") -> ".identifier("
        else -> ".location("
    }

	replacements {
		string {
			direction = eval(current.version, ">1.21.11")
			replace("level.random", "level.getRandom()")
		}


        string {
            direction = eval(current.version, "<=1.19.4")
            replace("MapColor", "MaterialColor")
        }

        string {
            direction = eval(current.version, "<=1.19.4")
            replace(".pushReaction(PushReaction.DESTROY)", "/*Removed push reaction*/")
        }

        string {
            direction = eval(current.version, "<=1.19.4")
            replace(").mapColor(", "net.minecraft.world.level.material.Material.STONE, ")
        }

        string {
            direction = eval(current.version, "<=1.20.1")
            replace("AdvancementType", "FrameType")
        }

        string {
            direction = eval(current.version, "<=1.20.1")
            replace("net.minecraft.advancements.AdvancementType", "net.minecraft.advancements.FrameType")
        }

        string {
            direction = eval(current.version, ">1.21.10")
            replace("ResourceLocation", "Identifier")
        }

        string {
            direction = eval(current.version, ">1.21.10")
            replace("net.minecraft.Util", "net.minecraft.util.Util")
        }

        string {
            direction = eval(current.version, ">1.21.10")
            replace("critereon", "criterion")
        }

        string {
            direction = eval(current.version, ">1.21.10")
            replace("entity, random", "level, entity, random")
        }

        string {
            direction = eval(current.version, ">1.21.10")
            replace("net.minecraft.world.entity.npc", "net.minecraft.world.entity.npc.villager")
        }

        string {
            direction = eval(current.version, ">=1.21.9")
            replace(".noCollission()", ".noCollision()")
        }

        string {
            direction = eval(current.version, "=1.21.3")
            replace("net.minecraft.client.resources.model.EquipmentClientInfo", "net.minecraft.world.item.equipment.EquipmentModel")
        }

        string {
            direction = eval(current.version, "<=1.21.3")
            replace("net.minecraft.client.data.models", "net.minecraft.data.models")
        }

        for (cls in listOf(Pair("EquipmentClientInfo", Pair("EquipmentModel", "=1.21.3")))) {
            string {
                direction = eval(node.metadata.version, cls.second.second)
                replace("${cls.first};", "${cls.second.first};")
            }

            string {
                direction = eval(node.metadata.version, cls.second.second)
                replace("${cls.first} ", "${cls.second.first} ")
            }

            string {
                direction = eval(node.metadata.version, cls.second.second)
                replace("${cls.first}>", "${cls.second.first}>")
            }

            string {
                direction = eval(node.metadata.version, cls.second.second)
                replace("${cls.first}.", "${cls.second.first}.")
            }
        }

        string {
            direction = eval(current.version, "<=1.20.4")
            replace("BootstrapContext", "BootstapContext")
        }

        string {
            direction = eval(current.version, "<=1.21")
            replace("ToolMaterial ", "Tier ")
        }

        string {
            direction = eval(current.version, "<=1.21")
            replace("net.minecraft.world.item.equipment.ArmorMaterial", "net.minecraft.world.item.ArmorMaterial")
        }

        string {
            direction = eval(current.version, "<=1.21")
            replace("ToolMaterial;", "Tier;")
        }

        string {
            direction = eval(current.version, "<=1.21.3")
            replace("net.minecraft.client.data.models.MultiVariant", "net.minecraft.client.renderer.block.model.MultiVariant")
        }

		for (cls in listOf("TagRegistration")) {
			string {
				direction = eval(node.metadata.version, "<=1.20.4")
				replace(
					"net.fabricmc.fabric.impl.tag.convention.v2.$cls;",
					"net.fabricmc.fabric.impl.tag.convention.$cls;"
				)
			}
		}

		for (cls in listOf("ConventionalItemTags", "ConventionalBlockTags")) {
			string {
				direction = eval(node.metadata.version, "<=1.20.4")
				replace(
					"net.fabricmc.fabric.api.tag.convention.v2.$cls;",
					"net.fabricmc.fabric.api.tag.convention.v1.$cls;"
				)
			}
		}

		for (cls in listOf("FabricModelProvider")) {
			string {
				direction = eval(node.metadata.version, ">=1.21.5")
				replace(
					"import net.fabricmc.fabric.api.datagen.v1.provider.$cls;",
					"import net.fabricmc.fabric.api.client.datagen.v1.provider.$cls;"
				)
			}
		}

		string {
			direction = eval(node.metadata.version, "<=1.21")
			replace(
				"import net.minecraft.item.equipment.ArmorMaterial;",
				"import net.minecraft.item.ArmorMaterial;"
			)
		}

		string {
			direction = eval(node.metadata.version, "<=1.20.1")
			replace(
				"RecipeOutput ",
				"java.util.function.Consumer<net.minecraft.data.recipes.FinishedRecipe> "
			)
		}

		string {
			direction = eval(node.metadata.version, "<=1.20.1")
			replace(
				"de.cech12.bucketlib",
				"com.github.cech12.BucketLib"
			)
		}
	}
}

val includesBucketlib = stonecutter.eval(stonecutter.current.version, "<=1.20.1") && hasBucketlib

val isSnapshot = stonecutter.current.project.contains("snapshot", true)
val minecraftVersion = if(isSnapshot ||
    (findProperty("deps.minecraft") != null && findProperty("deps.minecraft") != "[VERSIONED]")) property("deps.minecraft")
else stonecutter.current.project

dependencies {
	minecraft("com.mojang:minecraft:${minecraftVersion}")

	if(checkSpecified("fabric_loader"))
		implementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
	if(checkSpecified("fabric_api"))
		implementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

	/*if(checkSpecified("cloth_config"))
		compileOnly("me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth_config")}") {
			exclude(group = "net.fabricmc.fabric-api")
		}
	if(checkSpecified("mod_menu"))
		implementation("com.terraformersmc:modmenu:${property("deps.mod_menu")}")

	if(checkSpecified("bucketlib")) {
		implementation("com.github.cech12.BucketLib:fabric:${property("deps.bucketlib")}")

		if(includesBucketlib)
			include("com.github.cech12.BucketLib:fabric:${property("deps.bucketlib")}")
	}*/
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
	withSourcesJar()

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
			url = uri("https://maven.paulem.ovh/releases")
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
		displayName = "SimpleOres Refabricated ${project.property("mod.version")}" // Optional, name of the file
		version = project.version.toString() // Optional, Inferred from project by default
		changelog = githubChangelog // Optional, in markdown format
		releaseType = if(!hasBucketlib) "beta" else "release" // Optional, use "release", "beta" or "alpha"
		gameVersions = VersionRangeParser.parseVersionRange(
            runtimeVersionToSnapshot("min_version_range") as String,
            runtimeVersionToSnapshot("max_version_range") as String
		)
		gameLoaders = listOf("fabric", "quilt")

		mainPublication.set(project.rootDir.toPath().resolve("dist").resolve(distFileName).toFile()) // Declares the publicated jar

		relations {
			depends {
				modrinth = "fabric-api"
				curseforge = "fabric-api"
			}
			optional {
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
			// Wrap configuration in runCatching so a failure configuring the CurseForge
			// publisher does not abort the rest of the unified publishing setup.
			runCatching {
				curseforge {
					token = curseforgeToken
					id = "1092987" // Required, must be a string, ID of CurseForge project

					gameVersions = if(isSnapshot) {
						listOf(stonecutter.current.project)
					} else {
						VersionRangeParser.parseVersionRange(
							project.property("min_version_range") as String,
							project.property("max_version_range") as String,
							VersionRangeParser.CompiledVersions.VersionType.RELEASE
						)
					}
				}
			}.onFailure { ex ->
				logger.warn("Failed to configure CurseForge publishing - continuing with other publishers: ${ex.message}")
			}
		}

		val modrinthToken = (project.findProperty("MODRINTH_TOKEN") ?: System.getenv("MODRINTH_TOKEN")) as String?
		if (modrinthToken != null) {
			// Same defensive wrapper for Modrinth
			runCatching {
				modrinth {
					token = modrinthToken
					id = "Boe3chj8" // Required, must be a string, ID of Modrinth project
				}
			}.onFailure { ex ->
				logger.warn("Failed to configure Modrinth publishing - continuing with other publishers: ${ex.message}")
			}
		}
	}
}
