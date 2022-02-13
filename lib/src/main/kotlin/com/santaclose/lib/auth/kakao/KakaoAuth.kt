package com.santaclose.lib.auth.kakao

import arrow.core.Either
import arrow.core.Either.Companion.catch
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.web.reactive.function.BodyInserters.fromFormData
import org.springframework.web.reactive.function.client.WebClient

class KakaoAuth(
    private val builder: WebClient.Builder,
    private val clientId: String,
    private val redirectUri: String,
    private val tokenUri: String = "https://kauth.kakao.com/oauth/token",
) {
    private val client by lazy {
        builder.build()
    }

    suspend fun getAccessToken(code: String): Either<Throwable, String> = catch {
        client
            .post()
            .uri(tokenUri)
            .body(
                fromFormData("grant_type", "authorization_code")
                    .with("client_id", clientId)
                    .with("redirect_uri", redirectUri)
                    .with("code", code)
            )
            .retrieve()
            .bodyToMono(KakaoTokenResponse::class.java)
            .map { it.access_token ?: throw Exception("토큰 정보가 없습니다") }
            .awaitFirst()
    }.mapLeft { Exception("kakao 토큰 발급 실패: ${it.message}", it) }
}
