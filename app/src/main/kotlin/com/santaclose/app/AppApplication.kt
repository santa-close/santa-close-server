package com.santaclose.app

import com.expediagroup.graphql.generator.directives.KotlinDirectiveWiringFactory
import com.santaclose.app.auth.directive.AuthSchemaDirectiveWiring
import com.santaclose.app.hook.CustomSchemaGeneratorHooks
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@ConfigurationPropertiesScan
@EntityScan(basePackages = ["com.santaclose.lib.entity"])
@SpringBootApplication
class AppApplication {
    @Bean
    fun wiringFactory() = KotlinDirectiveWiringFactory(
        manualWiring = mapOf("auth" to AuthSchemaDirectiveWiring())
    )

    @Bean
    fun hooks(wiringFactory: KotlinDirectiveWiringFactory) =
        CustomSchemaGeneratorHooks(wiringFactory)
}

fun main(args: Array<String>) {
    runApplication<AppApplication>(*args)
}
