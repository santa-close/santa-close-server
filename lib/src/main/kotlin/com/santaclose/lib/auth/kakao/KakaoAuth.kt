package com.santaclose.lib.auth.kakao

import arrow.core.Either
import arrow.core.Either.Companion.catch
import arrow.core.computations.either
import com.santaclose.lib.auth.Profile
import com.santaclose.lib.logger.logger
import io.netty.channel.ChannelOption
import java.time.Duration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.BodyInserters.fromFormData
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import reactor.netty.http.client.HttpClient

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
  private val logger = logger()

  suspend fun getProfile(code: String): Either<Throwable, Profile> =
    either<Throwable, Profile> {
      val tokenResponse = getAccessToken(code).bind()
      val userResponse = getUser(tokenResponse.accessToken).bind()

      userResponse.toProfile()
    }
      .tapLeft { logger.error(it.message, it) }

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
      .awaitBody()
  }

  suspend fun getUser(token: String): Either<Throwable, KakaoUserResponse> = catch {
    client.get().uri(userUri).header("Authorization", "Bearer $token").retrieve().awaitBody()
  }
}
