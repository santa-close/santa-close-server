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
  implementation("org.hibernate:hibernate-spatial")
  implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:2.0.2.RELEASE")
  implementation("com.expediagroup", "graphql-kotlin-spring-server", "5.4.1")
  implementation("io.jsonwebtoken:jjwt-api:0.11.5")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}
