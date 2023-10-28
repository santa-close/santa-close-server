@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.buildtools.native)
}

dependencies {
    implementation(project(":server-lib"))
    implementation(libs.spring.webflux)
    implementation(libs.spring.graphql)
    implementation(libs.spring.security)
    implementation(libs.spring.data.jpa)
    implementation(libs.hibernate.spatial)
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.0.0")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.0.0")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:3.0.0")
    implementation(libs.jjwt.api)

    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(libs.jjwt.jackson)

    annotationProcessor(libs.spring.processor)

    testImplementation(libs.spring.graphql.test)
    testImplementation(libs.spring.security.test)
}

tasks.bootJar {
    enabled = true
    archiveVersion.set("")
}

tasks.jar {
    enabled = false
}
