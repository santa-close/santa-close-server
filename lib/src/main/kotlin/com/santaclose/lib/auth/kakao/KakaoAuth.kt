package com.santaclose.lib.auth.kakao

import arrow.core.Either
import arrow.core.Either.Companion.catch
import io.netty.channel.ChannelOption
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.BodyInserters.fromFormData
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

class KakaoAuth(
    private val builder: WebClient.Builder,
    private val clientId: String,
    private val redirectUri: String,
    private val tokenUri: String = "https://kauth.kakao.com/oauth/token",
    private val userUri: String = "https://kapi.kakao.com/v2/user/me",
) {
    private val client: WebClient =
        HttpClient.create()
            .responseTimeout(Duration.ofSeconds(3))
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
            .let(::ReactorClientHttpConnector)
            .let { builder.clientConnector(it).build() }

    suspend fun getAccessToken(code: String): Either<Throwable, KakaoTokenResponse> = catch {
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
            .map { it.apply { validate() } }
            .awaitSingle()
    }.mapLeft { Exception("kakao 토큰 발급 실패: ${it.message}", it) }

    suspend fun getUser(token: String): Either<Throwable, KakaoUserResponse> = catch {
        client
            .get()
            .uri(userUri)
            .header("Authorization", "Bearer $token")
            .retrieve()
            .bodyToMono(KakaoUserResponse::class.java)
            .map { it.apply { validate() } }
            .awaitSingle()
    }.mapLeft { Exception("kakao 사용자 조회 실패: ${it.message}", it) }
}
