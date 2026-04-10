pluginManagement {
	repositories {
		maven {
			name = "Fabric"
			url = uri("https://maven.fabricmc.net/")
		}
		maven { url = uri("https://maven.architectury.dev/") }
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
	}
}

plugins {
	id("dev.kikugie.stonecutter") version "0.9"
}

stonecutter {
	create(rootProject) {
		fun match(version: String, vararg loaders: String) = loaders
			.forEach { version(version, version).buildscript = "build.$it.gradle.kts" }

		match("1.19.4", "mojmaps")
		match("1.20.1", "mojmaps")
		match("1.20.4", "mojmaps")
		match("1.21", "mojmaps")
		match("1.21.3", "mojmaps")
		match("1.21.5", "mojmaps")
		match("1.21.6", "mojmaps")
		match("1.21.9", "mojmaps")
		match("1.21.11", "mojmaps")
		match("26.1", "deobf")
		match("26.2", "deobf")

		vcsVersion = "1.21.6"
	}
}