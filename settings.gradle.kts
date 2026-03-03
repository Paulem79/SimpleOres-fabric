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
		maven {
			name = "legacy-fabric"
			url = uri("https://maven.legacyfabric.net/")
		}
	}
}

plugins {
	id("dev.kikugie.stonecutter") version "0.9-alpha.7"
}

stonecutter {
	create(rootProject) {
		version("1.12.2")

		vcsVersion = "1.12.2"
	}
}