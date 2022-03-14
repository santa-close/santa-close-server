package com.santaclose.lib.s3Upload

import arrow.core.Either.Companion.catch
import aws.sdk.kotlin.runtime.auth.credentials.Credentials
import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.runtime.endpoint.AwsEndpoint
import aws.sdk.kotlin.runtime.endpoint.StaticEndpointResolver
import aws.sdk.kotlin.services.s3.S3Client
import aws.smithy.kotlin.runtime.content.ByteStream

class S3Uploader private constructor(
    private val s3Client: S3Client,
) {
    companion object {
        fun create(endpoint: String, awsRegion: String, accessKey: String, secretKey: String) = S3Uploader(
            S3Client {
                region = awsRegion
                endpointResolver = StaticEndpointResolver(AwsEndpoint(url = endpoint))
                credentialsProvider = StaticCredentialsProvider(Credentials(accessKey, secretKey))
            }
        )

    fun createByClient(s3Client: S3Client) = S3Uploader(s3Client)
  }

    suspend fun upload(bucket: String, path: String, data: ByteStream, contentType: String) = catch {
        s3Client.putObject {
            this.bucket = bucket
            key = path
            body = data
            this.contentType = contentType
        }
    }
  }
}
