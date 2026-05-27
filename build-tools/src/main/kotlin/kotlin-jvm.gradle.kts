package convention

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("jvm")
}

val libs = the<LibrariesForLibs>()

kotlin {
    jvmToolchain(21)
}

dependencies {
    "testImplementation"(libs.bundles.jvmTest.implementation)
    "testRuntimeOnly"(libs.bundles.jvmTest.runtime)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    testLogging {
        events(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED,
        )
    }
}
