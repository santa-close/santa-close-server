package com.santaclose.app.config

import com.santaclose.lib.auth.kakao.KakaoApiService
import com.santaclose.lib.auth.kakao.KakaoAuthService
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient

val kakaoAuthService: KakaoAuthService =
    HttpServiceProxyFactory
        .builder(
            WebClientAdapter.forClient(
                WebClient.builder().baseUrl("https://kauth.kakao.com").build(),
            ),
        )
        .build()
        .createClient()

val kakaoApiService: KakaoApiService =
    HttpServiceProxyFactory
        .builder(
            WebClientAdapter.forClient(
                WebClient.builder().baseUrl("https://kapi.kakao.com").build(),
            ),
        )
        .build()
        .createClient()
