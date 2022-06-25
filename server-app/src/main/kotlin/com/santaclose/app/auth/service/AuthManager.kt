package com.santaclose.app.auth.service

import com.santaclose.app.config.KakaoConfig
import com.santaclose.lib.auth.kakao.KakaoAuth
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class AuthManager(
    kakaoConfig: KakaoConfig,
    builder: WebClient.Builder,
) {
    private val kakaoAuth = KakaoAuth(builder, kakaoConfig.clientId, kakaoConfig.redirectUri)

    fun getProfile(code: String) = runBlocking { kakaoAuth.getProfile(code) }
}
