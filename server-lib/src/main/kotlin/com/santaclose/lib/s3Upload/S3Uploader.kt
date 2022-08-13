package com.santaclose.lib.s3Upload

import arrow.core.Either.Companion.catch
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import java.io.InputStream

class S3Uploader private constructor(private val s3: AmazonS3) {
    companion object {
        fun create(endpoint: String, awsRegion: String, accessKey: String, secretKey: String) =
            AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(
                    AwsClientBuilder.EndpointConfiguration(endpoint, awsRegion),
                )
                .withCredentials(
                    AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey)),
                )
                .build()
                .let(::S3Uploader)

        fun createByClient(s3: AmazonS3) = S3Uploader(s3)
    }

    fun upload(bucket: String, path: String, data: InputStream, contentType: String) = catch {
        val metadata = ObjectMetadata().also { it.contentType = contentType }

        s3.putObject(bucket, path, data, metadata)
    }
}
