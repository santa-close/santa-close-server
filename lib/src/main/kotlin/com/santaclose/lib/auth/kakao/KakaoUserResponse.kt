package com.santaclose.lib.auth.kakao

import com.santaclose.lib.auth.Profile

data class KakaoUserResponse(
  val id: Long,
  val kakao_account: KakaoAccountResponse,
) {
  fun toProfile() =
    Profile(
      id = "$id",
      name = kakao_account.profile.nickname,
      email = kakao_account.email,
    )
}

data class KakaoAccountResponse(
  val profile: KakaoProfileResponse,
  val email: String,
)

data class KakaoProfileResponse(val nickname: String)
