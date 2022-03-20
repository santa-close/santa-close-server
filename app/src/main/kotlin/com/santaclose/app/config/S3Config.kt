package com.santaclose.app.config

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "s3")
data class S3Config(
  @field:NotNull val endPoint: String,
  @field:NotBlank val region: String,
  @field:NotBlank val credentialsAccessKey: String,
  @field:NotBlank val credentialsSecretKey: String,
  @field:NotBlank val bucket: String,
  @field:NotBlank val domain: String,
)
