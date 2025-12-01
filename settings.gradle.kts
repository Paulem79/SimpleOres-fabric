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
	id("dev.kikugie.stonecutter") version "0.8-alpha.14"
}

stonecutter {
	create(rootProject) {
		versions("1.20.1", "1.20.4", "1.21", "1.21.3", "1.21.5", "1.21.6", "1.21.9", "1.21.11-snapshot")
		vcsVersion = "1.21.6"
	}
}