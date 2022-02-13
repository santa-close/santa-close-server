package com.santaclose.lib.auth.kakao

data class KakaoUserResponse(
    val id: Long?,
    val kakao_account: KakaoAccountResponse?,
) {
    fun validate() {
        if (
            id == null ||
            kakao_account?.email == null ||
            kakao_account.profile?.nickname == null
        )
            throw Exception("사용자 정보가 없습니다: $this")
    }
}

data class KakaoAccountResponse(
    val profile: KakaoProfileResponse?,
    val email: String?,
)

data class KakaoProfileResponse(
    val nickname: String?
)
