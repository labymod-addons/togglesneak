plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
    id("org.cadixdev.licenser") version ("0.6.1")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "org.example"
version = providers.environmentVariable("VERSION").getOrElse("1.1.3")

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

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
    plugins.apply("org.cadixdev.licenser")

    group = rootProject.group
    version = rootProject.version

    license {
        header(rootProject.file("gradle/LICENSE-HEADER.txt"))
        newLine.set(true)
    }
}