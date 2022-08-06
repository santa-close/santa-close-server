package com.santaclose.lib.auth.kakao

import arrow.core.Either
import arrow.core.Either.Companion.catch
import arrow.core.continuations.either
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.santaclose.lib.auth.Profile
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.http.HttpHeaders

class KakaoAuth(
    private val clientId: String,
    private val redirectUri: String,
    private val tokenUri: String = "https://kauth.kakao.com/oauth/token",
    private val userUri: String = "https://kapi.kakao.com/v2/user/me",
) {
    private val okhttp = OkHttpClient()
    private val objectMapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun getProfile(code: String): Either<Throwable, Profile> =
        either.eager {
            val tokenResponse = getAccessToken(code).bind()
            val userResponse = getUser(tokenResponse.accessToken).bind()

            userResponse.toProfile()
        }

    fun getAccessToken(code: String): Either<Throwable, KakaoTokenResponse> = catch {
        val request = Request
            .Builder()
            .url(tokenUri)
            .post(
                FormBody
                    .Builder()
                    .addEncoded("grant_type", "authorization_code")
                    .addEncoded("client_id", clientId)
                    .addEncoded("redirect_uri", redirectUri)
                    .addEncoded("code", code)
                    .build(),
            )
            .build()
        val response = okhttp.newCall(request).execute()

        if (response.isSuccessful) {
            objectMapper.readValue(response.body?.string(), KakaoTokenResponse::class.java)
        } else {
            throw Exception("${response.message} ${response.body?.string()}")
        }
    }

    fun getUser(token: String): Either<Throwable, KakaoUserResponse> = catch {
        val request = Request
            .Builder()
            .url(userUri)
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .build()
        val response = okhttp.newCall(request).execute()

        if (response.isSuccessful) {
            objectMapper.readValue(response.body?.string(), KakaoUserResponse::class.java)
        } else {
            throw Exception("${response.message} ${response.body?.string()}")
        }
    }
}
