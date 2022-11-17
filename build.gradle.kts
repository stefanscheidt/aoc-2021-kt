plugins {
    kotlin("jvm") version "1.7.20"
    id("com.diffplug.spotless") version "6.11.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.check {
    dependsOn("spotlessCheck")
}

spotless {
    kotlin {
        ktlint("0.47.1")
            .editorConfigOverride(mapOf(
                "ij_kotlin_allow_trailing_comma_on_call_site" to true,
                "ij_kotlin_allow_trailing_comma" to true,
            ))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("io.kotest:kotest-assertions-core:5.5.4")

    testImplementation(kotlin("script-runtime"))
}
