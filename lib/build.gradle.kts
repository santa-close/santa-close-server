plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    implementation(libs.spring.data.jpa)
    implementation(libs.graphql.kotlin)
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
