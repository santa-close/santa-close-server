package com.santaclose.app.auth.resolver

import arrow.core.flatMap
import com.expediagroup.graphql.server.operations.Mutation
import com.santaclose.app.auth.resolver.dto.AppAuthInfo
import com.santaclose.app.auth.resolver.dto.SignInAppInput
import com.santaclose.app.auth.service.AuthAppService
import com.santaclose.app.config.JWTConfig
import com.santaclose.lib.web.error.getOrThrow
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Component
@Validated
class AuthAppMutationResolver(
    private val authAppService: AuthAppService,
    private val jwtConfig: JWTConfig,
) : Mutation {
    suspend fun signIn(@Valid input: SignInAppInput): AppAuthInfo =
        authAppService.signIn(input.code)
            .flatMap { AppAuthInfo.by(it, jwtConfig.key, jwtConfig.expiredDays) }
            .getOrThrow()
}
