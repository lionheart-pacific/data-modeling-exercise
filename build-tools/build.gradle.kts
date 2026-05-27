plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.kotlinGradlePlugin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
