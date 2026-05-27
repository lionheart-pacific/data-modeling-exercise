plugins {
    id("convention.kotlin-jvm")
}

dependencies {
    implementation(project(":repository-contract"))
    implementation(libs.spring.jdbc)
    testImplementation(testFixtures(project(":repository-contract")))
}
