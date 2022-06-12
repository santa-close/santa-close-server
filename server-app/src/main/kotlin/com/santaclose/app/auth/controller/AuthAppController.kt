package com.santaclose.app.auth.controller

import arrow.core.flatMap
import com.santaclose.app.auth.controller.dto.AppAuthInfo
import com.santaclose.app.auth.controller.dto.SignInAppInput
import com.santaclose.app.auth.service.AuthAppService
import com.santaclose.app.config.JWTConfig
import com.santaclose.lib.web.error.getOrThrow
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

// @Component
// class AuthAppMutationResolver(
//    private val authAppService: AuthAppService,
//    private val jwtConfig: JWTConfig,
// ) : Mutation {
//    @GraphQLDescription("로그인 및 회원가입")
//    suspend fun signIn(input: SignInAppInput): AppAuthInfo =
//        authAppService
//            .signIn(input.code)
//            .flatMap { AppAuthInfo.by(it, jwtConfig.key, jwtConfig.expiredDays) }
//            .getOrThrow()
// }

@Controller
class AuthAppController(
    private val authAppService: AuthAppService,
    private val jwtConfig: JWTConfig,
) {

    @MutationMapping
    suspend fun signIn(@Argument input: SignInAppInput): AppAuthInfo =
        authAppService
            .signIn(input.code)
            .flatMap { AppAuthInfo.by(it, jwtConfig.key, jwtConfig.expiredDays) }
            .getOrThrow()
}
