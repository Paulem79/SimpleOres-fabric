pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
		maven {
			name = "Fabric"
			url = uri("https://maven.fabricmc.net/")
		}
		maven { url = uri("https://maven.architectury.dev/") }
		maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
		maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
		maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
	}
}

plugins {
	id("dev.kikugie.stonecutter") version "0.9.8"
	// Applique automatiquement le bon variant de Loom (mappings officiels ou Mojmaps) selon la
	// version ciblée : plus besoin de choisir/switcher entre fabric-loom et fabric-loom-remap.
	id("dev.kikugie.loom-back-compat") version "0.4.2"
}

rootProject.name = "SimpleOres"

stonecutter {
	kotlinController = true

	create(rootProject) {
		// Chaque version est déclinée par loader : "<version>-<loader>", avec son propre script de build
		fun match(version: String, vararg loaders: String) {
			for (loader in loaders) {
				version("$version-$loader", version).buildscript("build.$loader.gradle.kts")
			}
		}

		match("1.19.4", "fabric")
		match("1.20.1", "fabric")
		match("1.20.4", "fabric")
		match("1.20.6", "fabric")
		match("1.21", "fabric", "neoforge")
		match("1.21.3", "fabric", "neoforge")
		match("1.21.5", "fabric", "neoforge")
		match("1.21.6", "fabric", "neoforge")
		match("1.21.7", "fabric", "neoforge")
		match("1.21.9", "fabric", "neoforge")
		match("1.21.11", "fabric", "neoforge")
		match("26.1", "fabric", "neoforge")
		match("26.2", "fabric", "neoforge")
		match("26.3", "fabric", "neoforge")

		vcsVersion = "26.2-fabric"
	}
}
