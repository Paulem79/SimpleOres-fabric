pluginManagement {
	repositories {
		maven {
			name = "Fabric"
			url = uri("https://maven.fabricmc.net/")
		}
		maven { url = uri("https://maven.architectury.dev/") }
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.kikugie.dev/snapshots")
	}
}

plugins {
	id("dev.kikugie.stonecutter") version "0.7-alpha.23"
}

stonecutter {
	create(rootProject) {
		versions("1.21.4", "1.21.5", "1.21.6")
		vcsVersion = "1.21.6"
	}
}