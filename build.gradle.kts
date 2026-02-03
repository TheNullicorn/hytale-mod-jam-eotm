import fr.smolder.hytale.gradle.Patchline

plugins {
    id("java")
    id("fr.smolder.hytale.dev") version "0.1.0"
}

group = "me.nullicorn"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "hytale-release"
        url = uri("https://maven.hytale.com/release")
    }
    maven {
        name = "hytale-pre-release"
        url = uri("https://maven.hytale.com/pre-release")
    }
}

dependencies {
    compileOnly("com.hypixel.hytale:Server:2026.01.28-87d03be09")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

hytale {
    manifest {
        group = "Nullicorn"
        name = "Cognado"
        version = "1.0.0"
        description = "Submission for the 'Echoes of the Machine' Hytale mod jam"
        serverVersion = "*"
        main = "me.nullicorn.jam.eotm.CognadoPlugin"
        includesAssetPack = true
        disabledByDefault = false
        author("Nullicorn")
        dependency("Hytale:EntityModule", "*")
    }
    patchLine.set(Patchline.PRE_RELEASE)
    gameVersion.set("latest")
    autoUpdateManifest.set(true)
    vineflowerVersion.set("1.11.2")
    decompileFilter.set(listOf("com/hypixel/**"))
    includeDecompiledSources.set(true)
}

tasks.test {
    useJUnitPlatform()
}
