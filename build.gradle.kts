import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.6" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    id("com.expediagroup.graphql") version "5.4.1" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"

    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21" apply false
    kotlin("plugin.jpa") version "1.6.21" apply false

    jacoco
}

repositories { mavenCentral() }

subprojects {
    group = "com.santaclose"
    version = "0.0.1-SNAPSHOT"

    repositories { mavenCentral() }

    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "jacoco")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    java.sourceCompatibility = JavaVersion.VERSION_17

    dependencies {
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("io.arrow-kt:arrow-core:1.1.2")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("aws.sdk.kotlin:s3:0.15.2-beta")
        implementation("com.amazonaws:aws-java-sdk-s3:1.12.200")
        implementation("com.squareup.okhttp3:okhttp:4.9.3")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(module = "mockito-core")
        }
        testImplementation("io.projectreactor:reactor-test")
        testImplementation("io.mockk:mockk:1.12.3")
        testImplementation("io.kotest:kotest-runner-junit5:5.3.0")
        testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.2.5")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.1")
        testImplementation("com.ninja-squad:springmockk:3.1.1")
        testImplementation("com.navercorp.fixturemonkey:fixture-monkey-kotlin:0.3.3")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    jacoco { toolVersion = "0.8.7" }

    tasks.withType<JacocoReport> {
        reports {
            xml.required.set(true)
            csv.required.set(false)
            html.required.set(false)
        }
        dependsOn(tasks.test)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        reports {
            html.required.set(false)
            junitXml.required.set(true)
        }
        finalizedBy(tasks.jacocoTestReport)
    }
}
