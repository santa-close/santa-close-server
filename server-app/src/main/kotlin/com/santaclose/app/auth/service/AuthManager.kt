package com.santaclose.app.auth.service

import com.santaclose.app.config.KakaoConfig
import com.santaclose.lib.auth.kakao.KakaoAuth
import org.springframework.stereotype.Service

@Service
class AuthManager(
    kakaoConfig: KakaoConfig,
) {
    private val kakaoAuth = KakaoAuth(kakaoConfig.clientId, kakaoConfig.redirectUri)

    fun getProfile(code: String) = kakaoAuth.getProfile(code)
}
