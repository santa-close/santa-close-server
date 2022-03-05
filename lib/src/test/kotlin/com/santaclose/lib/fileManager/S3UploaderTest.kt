package com.santaclose.lib.fileManager

import aws.sdk.kotlin.runtime.auth.credentials.Credentials
import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.runtime.endpoint.AwsEndpoint
import aws.sdk.kotlin.runtime.endpoint.StaticEndpointResolver
import aws.sdk.kotlin.services.s3.S3Client
import io.kotest.common.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.utility.DockerImageName

internal class S3UploaderTest {

    companion object {
        val localstackImage = DockerImageName.parse("localstack/localstack:latest")
        lateinit var localstack: LocalStackContainer
        lateinit var s3Uploader: S3Uploader

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            localstack = LocalStackContainer(localstackImage)
                .withServices(LocalStackContainer.Service.S3).apply {
                    start()

                    val s3Client = S3Client {
                        region = region
                        endpointResolver =
                            StaticEndpointResolver(AwsEndpoint(url = getEndpointOverride(LocalStackContainer.Service.S3).toString()))
                        credentialsProvider = StaticCredentialsProvider(Credentials(accessKey, secretKey))
                    }
                    s3Uploader = S3Uploader.createByClient(s3Client)
                    runBlocking { s3Client.createBucket { bucket = "test" } }
                }
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            localstack.stop()
        }
    }

    @Test
    fun `test`() {
    }
}
