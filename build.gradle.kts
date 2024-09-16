plugins {
    kotlin("jvm") version "2.0.20"
    id("com.palantir.git-version") version "3.1.0"
}

group = "klaxon.klaxon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("it.unimi.dsi:fastutil:8.5.14")
    implementation("com.jsoizo:kotlin-csv-jvm:1.10.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

group = "klaxon.klaxon"
val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion()

kotlin {
    jvmToolchain(22)
}

tasks.test {
    useJUnitPlatform()
}