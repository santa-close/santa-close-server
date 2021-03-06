import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.kotlin.jpa) apply false

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
        val libs = rootProject.libs

        implementation(libs.bundles.kotlin.libs)
        implementation(libs.arrow.core)
        implementation(libs.spring.validation)
        implementation(libs.bundles.aws.libs)
        implementation(libs.okhttp)

        testImplementation(libs.spring.starter.test) {
            exclude(module = "mockito-core")
        }
        testImplementation(libs.reactor.test)
        testImplementation(libs.bundles.mockk)
        testImplementation(libs.bundles.kotest)
        testImplementation(libs.fixture.monkey.kotlin)
        testImplementation(libs.kotlinx.coroutines.test)
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
