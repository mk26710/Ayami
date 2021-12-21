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

java {
	targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

tasks.withType<JavaExec> {
    val f = File("build/run/")
    if (!f.exists()) {
        f.mkdir()
    }

    workingDir = f
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform(kotlin("bom")))
    // Use the Kotlin standard library.
    implementation(kotlin("stdlib"))
	
    // Kotlinx Coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC3")
	
	// Time management
	implementation("joda-time:joda-time:2.10.13")

    // Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    // Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}
