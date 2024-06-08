plugins {
    id("java-library")
}

repositories {
    mavenLocal()
}

dependencies {
    api(project(":api"))
}

labyModProcessor {
    referenceType = net.labymod.gradle.core.processor.ReferenceType.DEFAULT
}

tasks.compileJava {
    sourceCompatibility = JavaVersion.VERSION_21.toString()
    targetCompatibility = JavaVersion.VERSION_21.toString()
}