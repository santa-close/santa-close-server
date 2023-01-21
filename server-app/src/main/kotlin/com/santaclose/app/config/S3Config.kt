package com.santaclose.app.config

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "s3")
data class S3Config(
    @field:NotBlank val endPoint: String,
    @field:NotBlank val region: String,
    @field:NotBlank val credentialsAccessKey: String,
    @field:NotBlank val credentialsSecretKey: String,
    @field:NotBlank val bucket: String,
    @field:NotBlank val domain: String,
)
