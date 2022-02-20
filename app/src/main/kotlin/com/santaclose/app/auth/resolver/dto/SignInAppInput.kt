package com.santaclose.app.auth.resolver.dto

import javax.validation.constraints.NotBlank

data class SignInAppInput(
    @field:NotBlank
    val code: String,
    val type: SignInType,
)
