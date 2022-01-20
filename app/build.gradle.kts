plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    implementation(project(":lib"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:1.1.0.RELEASE")
    implementation("com.expediagroup", "graphql-kotlin-spring-server", "5.3.1")
}
