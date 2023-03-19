package com.santaclose.lib.auth.kakao

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.PostExchange

interface KakaoAuthService {
    @PostExchange(
        value = "/oauth/token",
        contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    )
    fun getAccessToken(
        @RequestParam("grant_type") grantType: String,
        @RequestParam("client_id") clientId: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("code") code: String,
    ): KakaoTokenResponse
}
