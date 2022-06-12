@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(project(":lib"))
    implementation(libs.spring.webflux)
    implementation(libs.spring.graphql)
    implementation(libs.spring.security)
    implementation(libs.spring.data.jpa)
    implementation(libs.hibernate.spatial)
    implementation(libs.kotlin.jdsl)
    implementation(libs.graphql.kotlin)
    implementation(libs.jjwt.api)

    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(libs.jjwt.jackson)

    annotationProcessor(libs.spring.processor)

    testImplementation(libs.spring.graphql.test)
    testImplementation(libs.spring.security.test)
}
