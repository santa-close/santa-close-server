package com.santaclose.app.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@ConstructorBinding
@ConfigurationProperties(prefix = "kakao")
@Validated
data class KakaoConfig(
    @field:NotBlank
    val clientId: String,
    @field:NotBlank
    val redirectUri: String,
)
