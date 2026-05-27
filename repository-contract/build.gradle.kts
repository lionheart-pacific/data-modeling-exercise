plugins {
    id("convention.kotlin-jvm")
    `java-test-fixtures`
}

dependencies {
    testFixturesApi(libs.junit.jupiter)
    testFixturesApi(libs.strikt.core)
}
