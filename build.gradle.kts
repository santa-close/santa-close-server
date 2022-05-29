import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.6.6" apply false
  id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
  id("com.expediagroup.graphql") version "5.3.2" apply false
  id("com.ncorti.ktfmt.gradle") version "0.8.0"

  kotlin("jvm") version "1.6.20"
  kotlin("plugin.spring") version "1.6.20" apply false
  kotlin("plugin.jpa") version "1.6.20" apply false

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
  apply(plugin = "com.ncorti.ktfmt.gradle")

  java.sourceCompatibility = JavaVersion.VERSION_11

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
    testImplementation("io.kotest:kotest-assertions-core:5.2.3")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.2.5")
    testImplementation("com.ninja-squad:springmockk:3.1.1")
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-kotlin:0.3.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict")
      jvmTarget = "11"
    }
  }

  ktfmt { googleStyle() }

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
