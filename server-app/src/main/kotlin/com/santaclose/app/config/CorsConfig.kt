package com.santaclose.app.config

import org.springframework.http.HttpMethod
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

fun corsWebFilter(): CorsWebFilter {
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
