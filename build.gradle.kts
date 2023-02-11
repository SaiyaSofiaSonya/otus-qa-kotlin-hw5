import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("io.qameta.allure") version "2.11.2"
}

group = "org.otus"
version = "1.0-SNAPSHOT"

val kotestVersion = "5.5.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.google.code.gson:gson:2.10")
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    // https://mavenlibs.com/maven/dependency/io.qameta.allure/allure-okhttp
    implementation("io.qameta.allure:allure-okhttp:2.17.1")

    testImplementation("io.kotest.extensions:kotest-extensions-allure:1.2.0")}

tasks.test {
    useJUnitPlatform()
    finalizedBy("allureReport")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

allure {
    report{
        version.set("2.17.1")

    }
}