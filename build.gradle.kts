import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import org.ajoberstar.grgit.Grgit

plugins {
    java
    id("com.diffplug.spotless") version "6.25.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.ajoberstar.grgit") version "5.2.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
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
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("com.mojang:authlib:1.5.25")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("org.bstats:bstats-base:3.0.2")
    implementation("com.github.cryptomorin:XSeries:9.8.1")
    implementation("dev.notmyfault.serverlib:ServerLib:2.3.4")
    implementation("io.papermc:paperlib:1.0.8")
    compileOnly("org.apache.logging.log4j:log4j-api:2.22.1")
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

spotless {
    java {
        licenseHeaderFile(rootProject.file("HEADER.txt"))
        target("**/*.java")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    targetCompatibility = JavaVersion.VERSION_1_8
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileJava {
        options.release.set(8)
    }
    shadowJar {
        archiveClassifier.set(null as String?)
        dependencies {
            relocate("com.cryptomorin.xseries", "net.arcaniax.buildersutilities.xseries") {
                include(dependency("com.github.cryptomorin:XSeries:9.8.1"))
            }
            relocate("org.bstats", "net.arcaniax.buildersutilities.metrics") {
                include(dependency("org.bstats:bstats-base:3.0.2"))
                include(dependency("org.bstats:bstats-bukkit:3.0.2"))
            }
            relocate("io.papermc.lib", "net.arcaniax.buildersutilities.paperlib") {
                include(dependency("io.papermc:paperlib:1.0.8"))
            }
            relocate("org.incendo.serverlib", "net.arcaniax.buildersutilities.serverlib") {
                include(dependency("dev.notmyfault.serverlib:ServerLib:2.3.4"))
            }
        }
        minimize()
    }
    build {
        dependsOn(shadowJar)
    }
}

bukkit {
    main = "net.arcaniax.buildersutilities.BuildersUtilities"
    authors = listOf("Ktar5", "Arcaniax", "TheMeinerLP")
    website = "https://www.spigotmc.org/resources/42361/"
    apiVersion = "1.13"
    commands {
        register("butil") {
            aliases = listOf("bu")
            description = "open toggle menu"
            permission = "builders.util.gui"
        }
        register("banner") {
            aliases = listOf("bm")
            description = "opens banner creator"
            permission = "builders.util.banner"
        }
        register("armorcolor") {
            description = "opens armor color creator"
            permission = "builders.util.color"
            aliases = listOf("color")
        }
        register("noclip") {
            aliases = listOf("nc")
            description = "Toggles NoClip."
            permission = "builders.util.noclip"
        }
        register("advfly") {
            aliases = listOf("af")
            description = "Toggles Advanced fly."
            permission = "builders.util.advancedfly"
        }
        register("nv") {
            description = "Toggles Nightvision."
            permission = "builders.util.nightvision"
            aliases = listOf("n")
        }
        register("secretblocks") {
            description = "Opens secret blocks menu"
            permission = "builders.util.secretblocks"
            aliases = listOf("blocks")
        }
        val aliasPermission = "builders.util.aliases"
        val aliasDescription = "alias"
        val commands = listOf("/r", "/s", "/c", "/f", "/pa", "/derot", "/twist", "/scale", "/cuboid", "/flip", "/convex", "ws",
                "fs")
        commands.forEach {
            register(it) {
                description = aliasDescription
                permission = aliasPermission
            }
        }
        permissions {
            register("builders.util.trapdoor") {
                default = BukkitPluginDescription.Permission.Default.TRUE
            }
            register("builders.util.slabs") {
                default = BukkitPluginDescription.Permission.Default.TRUE
            }
            register("builders.util.terracotta") {
                default = BukkitPluginDescription.Permission.Default.TRUE
            }
            register("builders.util.nightvision") {
                default = BukkitPluginDescription.Permission.Default.OP
            }
            register("builders.util.noclip") {
                default = BukkitPluginDescription.Permission.Default.OP
            }
            register("builders.util.advancedfly") {
                default = BukkitPluginDescription.Permission.Default.OP
            }
        }
    }
}

