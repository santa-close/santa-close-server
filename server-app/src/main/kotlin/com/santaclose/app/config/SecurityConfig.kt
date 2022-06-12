package com.santaclose.app.config

import com.santaclose.app.auth.context.parser.ServerRequestParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(
    private val serverRequestParser: ServerRequestParser,
) {

    @Bean
    fun springWebFilterChain(
        http: ServerHttpSecurity,
        manager: AuthenticationManager,
    ): SecurityWebFilterChain =
        http {
            csrf { disable() }
            authorizeExchange {
                authorize(anyExchange, permitAll)
            }
            httpBasic { disable() }
            addFilterAt(jwtAuthenticationFilter(manager), SecurityWebFiltersOrder.AUTHENTICATION)
        }

    private fun jwtAuthenticationFilter(manager: ReactiveAuthenticationManager): AuthenticationWebFilter {
        val bearerAuthenticationFilter = AuthenticationWebFilter(manager)

        bearerAuthenticationFilter.setServerAuthenticationConverter { exchange ->
            serverRequestParser.parse(exchange.request).fold(
                ifEmpty = { Mono.empty() },
                ifSome = {
                    UsernamePasswordAuthenticationToken(
                        /* principal = */ it.id,
                        /* credentials = */ it.role,
                        /* authorities = */ listOf(SimpleGrantedAuthority("ROLE_${it.role}"))
                    ).toMono()
                }
            )
        }

        return bearerAuthenticationFilter
    }
}
