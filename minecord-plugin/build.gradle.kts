group = rootProject.group
version = rootProject.version

dependencies {
    implementation(project(":minecord-core"))
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    compileKotlin {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    }

    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
        }
    }

    shadowJar {
        archiveBaseName.set(project.name)
        archiveVersion.set("")
        archiveClassifier.set("")
    }
}