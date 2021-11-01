val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val arrow_version: String by project


plugins {
    application
    kotlin("jvm") version "1.5.31"
    kotlin("kapt") version "1.5.31"
}

group = "io.github.dlopezgarsco"
version = "0.0.1"
application {
    mainClass.set("io.github.dlopezgarsco.ApplicationKt")
}

repositories {
    mavenCentral()
    maven ("https://dl.bintray.com/arrow-kt/arrow-kt/")
}

dependencies {
    implementation("io.arrow-kt:arrow-core:$arrow_version")
    implementation("io.arrow-kt:arrow-syntax:$arrow_version")
    kapt("io.arrow-kt:arrow-meta:$arrow_version")

    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-locations:$ktor_version")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")

    implementation("com.h2database:h2:1.4.200")
    implementation("org.jetbrains.exposed:exposed-core:0.36.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.36.1")
    implementation("com.zaxxer:HikariCP:5.0.0")
    implementation("org.flywaydb:flyway-core:8.0.2")

    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
}