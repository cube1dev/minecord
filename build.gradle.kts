plugins {
    kotlin("jvm") version "1.7.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.cube1"
version = "2.1.0"

allprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.github.johnrengelman.shadow")

    java.toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

subprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("net.dv8tion:JDA:5.0.0-alpha.12") {
            exclude(module = "opus-java")
        }
        compileOnly("org.apache.logging.log4j:log4j-core:2.17.2")
        compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    }
}