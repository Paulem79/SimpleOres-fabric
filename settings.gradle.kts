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
	id("dev.kikugie.stonecutter") version "0.9.5"
}

stonecutter {
	// Activation de la logique centralisée comme YACL
	kotlinController = true
	centralScript = "build.gradle.kts"

	create(rootProject) {
		// Le nom du projet devient "version-mapping" (ex: "26.1-deobf")
		// alors que la version cible Minecraft reste "version"
		fun match(version: String, mapping: String) {
			version("$version-$mapping", version)
		}

		match("1.19.4", "mojmaps")
		match("1.20.1", "mojmaps")
		match("1.20.4", "mojmaps")
		match("1.20.6", "mojmaps")
		match("1.21", "mojmaps")
		match("1.21.3", "mojmaps")
		match("1.21.5", "mojmaps")
		match("1.21.6", "mojmaps")
		match("1.21.7", "mojmaps")
		match("1.21.9", "mojmaps")
		match("1.21.11", "mojmaps")
		match("26.1", "deobf")
		match("26.2", "deobf")

		// IMPORTANT : La version VCS doit correspondre exactement au nouveau format de nom
		vcsVersion = "1.21.6-mojmaps"
	}
}