@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(libs.spring.data.jpa)
    implementation(libs.spring.webflux)
    implementation(libs.hibernate.spatial)

    runtimeOnly(libs.database.h2)
    runtimeOnly(libs.connector.mysql)

    testImplementation(libs.okhttp.mock.webserver)
    testImplementation(platform(libs.testcontainer.bom))
    testImplementation(libs.testcontainer.junit)
    testImplementation(libs.testcontainer.localstack)
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
