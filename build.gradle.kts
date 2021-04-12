import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.cadixdev.gradle.licenser.LicenseExtension

plugins {
    id("java")
    id("java-library")
    id("org.cadixdev.licenser") version "0.5.1"
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
    implementation(enforcedPlatform("org.apache.logging.log4j:log4j-bom:2.8.1") {
        because("Spigot provides Log4J (sort of, not in API, implicitly part of server)")
    })
    implementation("org.apache.logging.log4j:log4j-api")
    implementation("org.bstats:bstats-bukkit:2.2.1")
    implementation("org.bstats:bstats-base:2.2.1")
    implementation("com.github.cryptomorin:XSeries:7.9.1")
}

configurations.findByName("compileClasspath")?.apply {
    resolutionStrategy.componentSelection {
        withModule("org.slf4j:slf4j-api") {
            reject("No SLF4J allowed on compile classpath")
        }
    }
}

var rootVersion by extra("2.0.0")
var buildNumber by extra("")

if (project.hasProperty("buildnumber")) {
    buildNumber = project.properties["buildnumber"] as String
} else {
    var index = "local"
    buildNumber = index.toString()
}

version = String.format("%s-%s", rootVersion, buildNumber)

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set(null as String?)
    dependencies {
        relocate("com.cryptomorin.xseries", "net.arcaniax.utils") {
            include(dependency("com.github.cryptomorin:XSeries:7.9.1"))
        }
        relocate("org.bstats", "net.arcaniax.buildersutilities.metrics") {
            include(dependency("org.bstats:bstats-base:2.2.1"))
            include(dependency("org.bstats:bstats-bukkit:2.2.1"))
        }
    }
    minimize()
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
