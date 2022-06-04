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
import org.springframework.http.HttpMethod
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@EnableJpaAuditing
@ConfigurationPropertiesScan
@EntityScan(basePackages = ["com.santaclose.lib.entity"])
@SpringBootApplication
class AppApplication {
    @Bean
    fun wiringFactory() =
        KotlinDirectiveWiringFactory(manualWiring = mapOf("auth" to AuthSchemaDirectiveWiring()))

    @Bean
    fun hooks(wiringFactory: KotlinDirectiveWiringFactory) = CustomSchemaGeneratorHooks(wiringFactory)

    @Bean
    fun corsFilter(): CorsWebFilter {
        val config =
            CorsConfiguration().apply {
                allowCredentials = true
                addAllowedOrigin("https://studio.apollographql.com")
                addAllowedHeader("*")
                addAllowedMethod(HttpMethod.POST.name)
            }

        return UrlBasedCorsConfigurationSource()
            .apply { registerCorsConfiguration("/graphql/**", config) }
            .let(::CorsWebFilter)
    }
}

fun main(args: Array<String>) {
    runApplication<AppApplication>(*args)
}
