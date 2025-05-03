plugins {
    java
}

tasks.withType<JavaCompile>().configureEach {
    JavaVersion.VERSION_21.toString().also {
        sourceCompatibility = it
        targetCompatibility = it
    }
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.kohsuke:github-api:1.327")
    implementation("com.google.code.gson:gson:2.10.1")
}


