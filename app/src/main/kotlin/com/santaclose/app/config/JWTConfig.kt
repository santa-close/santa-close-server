package com.santaclose.app.config

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.crypto.SecretKey
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
@Validated
data class JWTConfig(
    @field:NotBlank
    val secret: String,

    @field:Positive
    val expiredDays: Int,
) {
    val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
}
