package com.santaclose.lib.auth.kakao

import arrow.core.Either
import arrow.core.Either.Companion.catch
import arrow.core.continuations.either
import com.santaclose.lib.auth.Profile
import org.springframework.http.HttpHeaders

class KakaoAuth(
    private val kakaoAuthService: KakaoAuthService,
    private val kakaoApiService: KakaoApiService,
    private val clientId: String,
    private val redirectUri: String,
) {
    fun getProfile(code: String): Either<Throwable, Profile> =
        either.eager {
            val tokenResponse = getAccessToken(code).bind()
            val userResponse = getUser(tokenResponse.accessToken).bind()

            userResponse.toProfile()
        }

    fun getAccessToken(code: String): Either<Throwable, KakaoTokenResponse> = catch {
        kakaoAuthService.getAccessToken(
            "authorization_code",
            clientId,
            redirectUri,
            code,
        )
    }

    fun getUser(token: String): Either<Throwable, KakaoUserResponse> = catch {
        kakaoApiService.getUser(
            mapOf(HttpHeaders.AUTHORIZATION to "Bearer $token"),
        )
    }
}
