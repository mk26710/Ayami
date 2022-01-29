import java.io.File
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val exposedVersion = "0.37.3"

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    application
}

group = "moe.kadosawa"
version = "0.0.1"

application {
    mainClass.set("moe.kadosawa.ayami.MainKt")
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8")
}

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
    // Kotlin CLI
    implementation("org.jetbrains.kotlinx:kotlinx-cli-jvm:0.3.4")
    // Kotlinx Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.0")
    // Serializer
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    // Time management
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")

    // Guava
    implementation("com.google.guava:guava:31.0.1-jre")

    // Apache Commons
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.commons:commons-text:1.9")
    implementation("org.apache.commons:commons-math3:3.6.1")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.2.10")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")

    // Discord API
    implementation("net.dv8tion:JDA:5.0.0-alpha.5") { exclude(module = "opus-java") }

    // Database
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.postgresql:postgresql:42.3.1")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

tasks.withType<JavaExec> {
    val f = File(".run/")
    if (!f.exists()) {
        f.mkdir()
    }

    workingDir = f
}
