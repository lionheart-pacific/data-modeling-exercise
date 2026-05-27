// The code in this file is a convention plugin — a Gradle mechanism for sharing reusable build logic.
// It lives in the `build-tools` included build (registered in the root settings.gradle.kts).
package convention

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin in JVM projects.
    kotlin("jvm")
}

val libs = the<LibrariesForLibs>()

kotlin {
    // Use a specific Java version to make it easier to work in different environments.
    jvmToolchain(21)
}

dependencies {
    "testImplementation"(libs.bundles.jvmTest.implementation)
    "testRuntimeOnly"(libs.bundles.jvmTest.runtime)
}

tasks.withType<Test>().configureEach {
    // Configure all test Gradle tasks to use JUnitPlatform.
    useJUnitPlatform()

    // Log information about all test results, not only the failed ones.
    testLogging {
        events(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED
        )
    }
}
