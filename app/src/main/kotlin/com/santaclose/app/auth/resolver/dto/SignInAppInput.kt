package com.santaclose.app.auth.resolver.dto

data class SignInAppInput(
  val code: String,
  val type: SignInType,
)
