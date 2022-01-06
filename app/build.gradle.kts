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
    implementation("com.expediagroup", "graphql-kotlin-spring-server", "5.3.1")
    implementation("com.querydsl:querydsl-jpa:5.0.0")
}
