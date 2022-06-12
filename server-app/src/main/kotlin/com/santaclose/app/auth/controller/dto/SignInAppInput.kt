package com.santaclose.app.auth.controller.dto

data class SignInAppInput(
    val code: String,
    val type: SignInType,
)
