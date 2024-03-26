import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.jpa")
    kotlin("plugin.spring")
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // SpringBoot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Database
    runtimeOnly("mysql:mysql-connector-java:${property("mysqlVersion")}")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("it.ozimov:embedded-redis:0.7.2")

    // AWS
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:${property("awsSpringCloudVersion")}"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs")
    implementation("software.amazon.awssdk:sns")
    implementation("com.amazonaws:aws-java-sdk:1.12.125")

    // Monitoring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter:1.19.3")
    testImplementation("org.testcontainers:localstack:1.15.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("-Xmx2048m", "-XX:+HeapDumpOnOutOfMemoryError")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}
