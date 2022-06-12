plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    implementation(project(":lib"))
    implementation(libs.spring.data.jpa)
    implementation(libs.hibernate.spatial)
    implementation(libs.kotlin.jdsl)
    implementation(libs.graphql.kotlin)
    implementation(libs.jjwt.api)

    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(libs.jjwt.jackson)

    annotationProcessor(libs.spring.processor)
}
