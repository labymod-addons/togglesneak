plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
    id("dev.yumi.gradle.licenser") version ("4.0.0")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "org.example"
version = providers.environmentVariable("VERSION").getOrElse("1.1.3")

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

license {
    rule(rootProject.file("gradle/LICENSE-HEADER.txt"))
    failOnMissingHeaderCommentHandler.set(false)
}

labyMod {
    defaultPackageName = "net.labymod.addons.togglesneak"

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    devLogin = true
                }
            }
        }
    }

    addonInfo {
        namespace = "togglesneak"
        displayName = "ToggleSneak"
        author = "LabyMedia GmbH"
        description = "Toggle your sneak mode"
        minecraftVersion = "*"
        version = rootProject.version.toString()
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")
    plugins.apply("dev.yumi.gradle.licenser")

    group = rootProject.group
    version = rootProject.version

    license {
        rule(rootProject.file("gradle/LICENSE-HEADER.txt"))
        failOnMissingHeaderCommentHandler.set(false)
    }

    extensions.findByType(JavaPluginExtension::class.java)?.apply {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}