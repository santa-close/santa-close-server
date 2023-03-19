package com.santaclose.app.auth.service

import com.santaclose.app.config.KakaoConfig
import com.santaclose.lib.auth.kakao.KakaoApiService
import com.santaclose.lib.auth.kakao.KakaoAuth
import com.santaclose.lib.auth.kakao.KakaoAuthService
import org.springframework.stereotype.Service

@Service
class AuthManager(
    kakaoAuthService: KakaoAuthService,
    kakaoApiService: KakaoApiService,
    kakaoConfig: KakaoConfig,
) {
    private val kakaoAuth = KakaoAuth(
        kakaoAuthService,
        kakaoApiService,
        kakaoConfig.clientId,
        kakaoConfig.redirectUri,
    )

    fun getProfile(code: String) = kakaoAuth.getProfile(code)
}
