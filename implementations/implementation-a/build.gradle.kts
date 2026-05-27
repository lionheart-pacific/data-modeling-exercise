plugins {
    id("convention.kotlin-jvm")
}

dependencies {
    implementation(project(":repository-contract"))
    testImplementation(testFixtures(project(":repository-contract")))
}
