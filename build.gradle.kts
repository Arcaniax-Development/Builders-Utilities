import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.cadixdev.gradle.licenser.LicenseExtension
import org.ajoberstar.grgit.Grgit

plugins {
    java

    id("org.cadixdev.licenser") version "0.6.1"
    id("com.github.johnrengelman.shadow") version "7.1.1"
    id("org.ajoberstar.grgit") version "4.1.1"

    idea
    eclipse
}

the<JavaPluginExtension>().toolchain {
    languageVersion.set(JavaLanguageVersion.of(16))
}

tasks.compileJava.configure {
    options.release.set(8)
}

configurations.all {
    attributes.attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 16)
}

repositories {
    mavenCentral()
    maven {
        name = "Paper"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        name = "Mojang"
        url = uri("https://libraries.minecraft.net/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("com.mojang:authlib:1.5.25")
    implementation("org.bstats:bstats-bukkit:2.2.1")
    implementation("org.bstats:bstats-base:2.2.1")
    implementation("com.github.cryptomorin:XSeries:8.5.0.1")
    implementation("dev.notmyfault.serverlib:ServerLib:2.3.1")
    implementation("io.papermc:paperlib:1.0.7")
    compileOnly("org.apache.logging.log4j:log4j-api:2.17.2")
}

var buildNumber by extra("")
ext {
    val git: Grgit = Grgit.open {
        dir = File("$rootDir/.git")
    }
    val commit: String? = git.head().abbreviatedId
    buildNumber = if (project.hasProperty("buildnumber")) {
        project.properties["buildnumber"] as String
    } else {
        commit.toString()
    }
}

version = String.format("%s-%s", rootProject.version, buildNumber)

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set(null as String?)
    dependencies {
        relocate("com.cryptomorin.xseries", "net.arcaniax.buildersutilities.xseries") {
            include(dependency("com.github.cryptomorin:XSeries:8.5.0.1"))
        }
        relocate("org.bstats", "net.arcaniax.buildersutilities.metrics") {
            include(dependency("org.bstats:bstats-base:2.2.1"))
            include(dependency("org.bstats:bstats-bukkit:2.2.1"))
        }
        relocate("io.papermc.lib", "net.arcaniax.buildersutilities.paperlib") {
            include(dependency("io.papermc:paperlib:1.0.7"))
        }
        relocate("org.incendo.serverlib", "net.arcaniax.buildersutilities.serverlib") {
            include(dependency("dev.notmyfault.serverlib:ServerLib:2.3.1"))
        }
    }
    minimize()
}

configure<LicenseExtension> {
    header.set(resources.text.fromFile(file("HEADER.txt")))
    newLine.set(false)
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
