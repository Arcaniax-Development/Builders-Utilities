import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.cadixdev.gradle.licenser.LicenseExtension
import org.ajoberstar.grgit.Grgit

plugins {
    id("java")
    id("java-library")
    id("org.cadixdev.licenser") version "0.6.1"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("org.ajoberstar.grgit") version "4.1.0"
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
    maven {
        name = "IntellectualSites"
        url = uri("https://mvn.intellectualsites.com/content/groups/public/")
    }
}

dependencies {
    compileOnlyApi("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnlyApi("com.mojang:authlib:1.5.25")
    implementation(enforcedPlatform("org.apache.logging.log4j:log4j-bom:2.14.1") {
        because("Spigot provides Log4J (sort of, not in API, implicitly part of server)")
    })
    implementation("org.apache.logging.log4j:log4j-api")
    implementation("org.bstats:bstats-bukkit:2.2.1")
    implementation("org.bstats:bstats-base:2.2.1")
    implementation("com.github.cryptomorin:XSeries:8.3.0")
    implementation("org.incendo.serverlib:ServerLib:2.2.1")
    implementation("io.papermc:paperlib:1.0.6")
}

configurations.findByName("compileClasspath")?.apply {
    resolutionStrategy.componentSelection {
        withModule("org.slf4j:slf4j-api") {
            reject("No SLF4J allowed on compile classpath")
        }
    }
}

var rootVersion by extra("2.1.1")
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

version = String.format("%s-%s", rootVersion, buildNumber)

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set(null as String?)
    dependencies {
        relocate("com.cryptomorin.xseries", "net.arcaniax.utils") {
            include(dependency("com.github.cryptomorin:XSeries:8.2.0"))
        }
        relocate("org.bstats", "net.arcaniax.buildersutilities.metrics") {
            include(dependency("org.bstats:bstats-base:2.2.1"))
            include(dependency("org.bstats:bstats-bukkit:2.2.1"))
        }
        relocate("io.papermc.lib", "net.arcaniax.buildersutilities.paperlib") {
            include(dependency("io.papermc:paperlib:1.0.6"))
        }
        relocate("org.incendo.serverlib", "net.arcaniax.buildersutilities.serverlib") {
            include(dependency("org.incendo.serverlib:ServerLib:2.2.1"))
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
