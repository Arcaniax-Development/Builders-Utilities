import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.diffplug.gradle.spotless.SpotlessPlugin
import org.ajoberstar.grgit.Grgit

plugins {
    java

    id("com.diffplug.spotless") version "8.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.ajoberstar.grgit") version "5.3.2"

    idea
    eclipse
}

the<JavaPluginExtension>().toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.compileJava.configure {
    options.release.set(8)
}

configurations.all {
    attributes.attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 17)
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
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("com.mojang:authlib:1.5.25")
    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation("org.bstats:bstats-base:3.1.0")
    implementation("com.github.cryptomorin:XSeries:13.4.0")
    implementation("dev.notmyfault.serverlib:ServerLib:2.3.7")
    implementation("io.papermc:paperlib:1.0.8")
    compileOnly("org.apache.logging.log4j:log4j-api:2.25.0")
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
            include(dependency("com.github.cryptomorin:XSeries:13.4.0"))
        }
        relocate("org.bstats", "net.arcaniax.buildersutilities.metrics") {
            include(dependency("org.bstats:bstats-base:3.1.0"))
            include(dependency("org.bstats:bstats-bukkit:3.1.0"))
        }
        relocate("io.papermc.lib", "net.arcaniax.buildersutilities.paperlib") {
            include(dependency("io.papermc:paperlib:1.0.8"))
        }
        relocate("org.incendo.serverlib", "net.arcaniax.buildersutilities.serverlib") {
            include(dependency("dev.notmyfault.serverlib:ServerLib:2.3.7"))
        }
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
