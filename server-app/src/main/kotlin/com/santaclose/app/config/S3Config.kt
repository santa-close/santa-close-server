package com.santaclose.app.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import javax.validation.constraints.NotBlank

@ConstructorBinding
@ConfigurationProperties(prefix = "s3")
data class S3Config(
    @field:NotBlank val endPoint: String,
    @field:NotBlank val region: String,
    @field:NotBlank val credentialsAccessKey: String,
    @field:NotBlank val credentialsSecretKey: String,
    @field:NotBlank val bucket: String,
    @field:NotBlank val domain: String,
)
