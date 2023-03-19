package com.santaclose.lib.auth.kakao

import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.GetExchange

interface KakaoApiService {
    @GetExchange("/v2/user/me")
    fun getUser(@RequestHeader header: Map<String, String>): KakaoUserResponse
}
