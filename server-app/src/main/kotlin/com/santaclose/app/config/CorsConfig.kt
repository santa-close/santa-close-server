package com.santaclose.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsWebFilter {
        val config = CorsConfiguration().apply {
            allowCredentials = true
            addAllowedOrigin("https://studio.apollographql.com")
            addAllowedHeader("*")
            addAllowedMethod(HttpMethod.POST)
        }

        return UrlBasedCorsConfigurationSource()
            .apply { registerCorsConfiguration("/graphql/**", config) }
            .let(::CorsWebFilter)
    }
}
