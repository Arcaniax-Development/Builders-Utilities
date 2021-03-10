import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.cadixdev.gradle.licenser.LicenseExtension

plugins {
    id("java")
    id("java-library")
    id("org.cadixdev.licenser") version "0.5.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = sourceCompatibility
}

repositories {
    mavenCentral()
    jcenter()
    maven {
        name = "Spigot"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "Mojang"
        url = uri("https://libraries.minecraft.net/")
    }
}

dependencies {
    compileOnlyApi("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    compileOnlyApi("com.mojang:authlib:1.5.25")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.8.1")
    implementation("org.bstats:bstats-bukkit:2.2.1")
    implementation("org.bstats:bstats-base:2.2.1")
    implementation("com.github.cryptomorin:XSeries:7.8.0")
}

version = "2.0.0"

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set(null as String?)
    dependencies {
        relocate("com.cryptomorin.xseries", "net.arcaniax.utils") {
            include(dependency("com.github.cryptomorin:XSeries:7.8.0"))
        }
        relocate("org.apache.logging.slf4j", "net.arcaniax.logging.apache") {
            include(dependency("org.apache.logging.log4j:log4j-slf4j-impl"))
        }
        relocate("org.slf4j", "net.arcaniax.logging.slf4j") {
            include(dependency("org.slf4j:slf4j-api"))
        }
        relocate("org.bstats", "net.arcaniax.buildersutilities.metrics") {
            include(dependency("org.bstats:bstats-base:2.2.1"))
            include(dependency("org.bstats:bstats-bukkit:2.2.1"))
        }
    }
}

configure<LicenseExtension> {
    header = rootProject.file("HEADER")
    newLine = false
    include("**/*.java")
}

tasks.named<Copy>("processResources") {
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}

tasks.named("build").configure {
    dependsOn("shadowJar")
}
