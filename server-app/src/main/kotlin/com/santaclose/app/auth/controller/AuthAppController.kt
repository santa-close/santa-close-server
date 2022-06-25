package com.santaclose.app.auth.controller

import arrow.core.flatMap
import com.santaclose.app.auth.controller.dto.AppAuthInfo
import com.santaclose.app.auth.controller.dto.SignInAppInput
import com.santaclose.app.auth.service.AuthAppService
import com.santaclose.app.config.JWTConfig
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.exception.getOrThrow
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class AuthAppController(
    private val authAppService: AuthAppService,
    private val jwtConfig: JWTConfig,
) {
    private val logger = logger()

    @MutationMapping
    fun signIn(@Argument input: SignInAppInput): AppAuthInfo =
        authAppService
            .signIn(input.code)
            .flatMap { AppAuthInfo.by(it, jwtConfig.key, jwtConfig.expiredDays) }
            .tapLeft { logger.error(it.message, it) }
            .getOrThrow()
}
