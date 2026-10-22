import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.diffplug.gradle.spotless.SpotlessPlugin

plugins {
    java

    id("com.diffplug.spotless") version "8.10.1"
    id("com.gradleup.shadow") version "9.6.1"

    idea
    eclipse
}

the<JavaPluginExtension>().toolchain {
    languageVersion.set(JavaLanguageVersion.of(25))
}

tasks.compileJava.configure {
    options.release.set(8)
}

configurations.all {
    attributes.attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 25)
}

repositories {
    mavenCentral()
    maven {
        name = "Paper"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "Mojang"
        url = uri("https://libraries.minecraft.net/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.3.build.+")
    compileOnly("com.mojang:authlib:1.5.25")
    implementation("org.bstats:bstats-bukkit:3.2.1")
    implementation("org.bstats:bstats-base:3.2.1")
    implementation("com.github.cryptomorin:XSeries:14.0.0")
    compileOnly("org.apache.logging.log4j:log4j-api:2.26.1")
}

version = "2.1.1-SNAPSHOT"

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set(null as String?)
    relocate("com.cryptomorin.xseries", "net.arcaniax.buildersutilities.xseries")
    relocate("org.bstats", "net.arcaniax.buildersutilities.metrics")
    dependencies {
        include(dependency("com.github.cryptomorin:XSeries:14.0.0"))
        include(dependency("org.bstats:bstats-base:3.2.1"))
        include(dependency("org.bstats:bstats-bukkit:3.2.1"))
    }
    minimize()
}

spotless {
    java {
        licenseHeaderFile(rootProject.file("HEADER.txt"))
        target("**/*.java")
    }
}

tasks.named<Copy>("processResources") {
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}

tasks.named("build").configure {
    dependsOn("shadowJar")
}
