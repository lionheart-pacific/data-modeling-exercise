dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

includeBuild("build-tools/")

include(":repository-contract")
include(":implementations:implementation-a")
include(":implementations:implementation-b")
include(":implementations:example")

rootProject.name = "data-modeling-exercise"
