package com.santaclose.lib.auth.kakao

import com.fasterxml.jackson.annotation.JsonProperty
import com.santaclose.lib.auth.Profile

data class KakaoUserResponse(
    val id: Long,
    @param:JsonProperty("kakao_account")
    @get:JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccountResponse,
) {
    fun toProfile() =
        Profile(
            id = "$id",
            name = kakaoAccount.profile.nickname,
            email = kakaoAccount.email,
        )
}

data class KakaoAccountResponse(
    val profile: KakaoProfileResponse,
    val email: String,
)

data class KakaoProfileResponse(val nickname: String)
