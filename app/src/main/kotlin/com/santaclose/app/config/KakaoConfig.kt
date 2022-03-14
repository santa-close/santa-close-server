package com.santaclose.app.config

import javax.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated

@ConstructorBinding
@ConfigurationProperties(prefix = "kakao")
@Validated
data class KakaoConfig(
  @field:NotBlank val clientId: String,
  @field:NotBlank val redirectUri: String,
)
