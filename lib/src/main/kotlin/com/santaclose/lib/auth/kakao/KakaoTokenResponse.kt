package com.santaclose.lib.auth.kakao

data class KakaoTokenResponse(val access_token: String?) {
    fun validate() {
        access_token ?: throw Exception("토큰 정보가 없습니다: $this")
    }
}
