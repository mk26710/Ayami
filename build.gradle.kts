import java.io.File
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "moe.kadosawa"
version = "0.0.1"

application {
    mainClass.set("moe.kadosawa.ayami.AppKt")
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8")
}

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
}

dependencies {
    // Use the Kotlin standard library.
    implementation(kotlin("stdlib"))
    // Kotlin CLI
    implementation("org.jetbrains.kotlinx:kotlinx-cli-jvm:0.3.3")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.2.9")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")

    // Time management
    implementation("joda-time:joda-time:2.10.13")

    // Discord API
    implementation("net.dv8tion:JDA:5.0.0-alpha.2") { exclude(module = "opus-java") }

    // Kotlinx Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC3")
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
