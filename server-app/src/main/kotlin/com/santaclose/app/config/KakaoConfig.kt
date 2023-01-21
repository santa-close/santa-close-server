package com.santaclose.app.config

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@ConfigurationProperties(prefix = "kakao")
@Validated
data class KakaoConfig(
    @field:NotBlank val clientId: String,
    @field:NotBlank val redirectUri: String,
)
