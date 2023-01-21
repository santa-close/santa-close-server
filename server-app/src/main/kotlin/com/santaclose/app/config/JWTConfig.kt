package com.santaclose.app.config

import io.jsonwebtoken.security.Keys
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import javax.crypto.SecretKey

@ConfigurationProperties(prefix = "jwt")
@Validated
data class JWTConfig(
    @field:NotBlank val secret: String,
    @field:Positive val expiredDays: Long,
) {
    val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
}
