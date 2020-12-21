import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecrell.gradle.licenser.LicenseExtension

plugins {
    id("java")
    id("net.minecrell.licenser") version "0.4.1"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    jcenter()
    maven {
        name = "Spigot"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    }
    maven {
        name = "bStats"
        url = uri("https://repo.codemc.org/repository/maven-public")
    }
    maven {
        name = "Mojang"
        url = uri("https://libraries.minecraft.net/")
    }
}

dependencies {
    "compileOnly"("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")
    "implementation"("com.mojang:authlib:1.5.25")
    "implementation"("org.apache.logging.log4j:log4j-slf4j-impl:2.8.1")
    "compile"("org.bstats:bstats-bukkit:1.8")
}

version = "2.0.0"

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    dependencies {
        include(dependency("org.apache.logging.log4j:log4j-slf4j-impl"))
        include(dependency("org.slf4j:slf4j-api"))
        include(dependency("org.bstats:bstats-bukkit:1.8"))
        relocate("org.apache.logging.slf4j", "net.arcaniax.logging.apache")
        relocate("org.slf4j", "net.arcaniax.logging.slf4j")
        relocate("org.bstats", "net.arcaniax.metrics")
    }
}

configure<LicenseExtension> {
    header = rootProject.file("HEADER")
    newLine = false
    include("**/*.java")
    exclude("**/XMaterial.java")
}

tasks.named<Copy>("processResources") {
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}

tasks.named("build").configure {
    dependsOn("shadowJar")
}
