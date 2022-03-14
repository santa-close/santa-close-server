package com.santaclose.lib.s3Upload

import aws.sdk.kotlin.runtime.auth.credentials.Credentials
import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.runtime.endpoint.AwsEndpoint
import aws.sdk.kotlin.runtime.endpoint.StaticEndpointResolver
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import io.kotest.assertions.throwables.shouldNotThrow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.utility.DockerImageName

internal class S3UploaderTest {

  companion object {
    private val localstackImage: DockerImageName =
      DockerImageName.parse("localstack/localstack:0.14.0")
    lateinit var localstack: LocalStackContainer
    lateinit var s3Uploader: S3Uploader
    lateinit var s3Client: S3Client

    @BeforeAll
    @JvmStatic
    fun beforeAll() {
      localstack = LocalStackContainer(localstackImage).withServices(LocalStackContainer.Service.S3)
      with(localstack) {
        start()
        s3Client =
          S3Client {
            region = this@with.region
            endpointResolver =
              StaticEndpointResolver(
                AwsEndpoint(url = getEndpointOverride(LocalStackContainer.Service.S3).toString())
              )
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

  @Nested
  inner class Upload {

    @Test
    fun `파일 업로드를 수행한다`() {
      runBlocking {
        // given
        val multipartFile = MockMultipartFile("fileName", "file content".toByteArray())
        // when
        s3Uploader.upload("test", "path", multipartFile)

        // then
        shouldNotThrow<Throwable> {
          s3Client.getObject(
            GetObjectRequest {
              bucket = "test"
              key = "path"
            }
          ) {}
        }
      }
    }
  }
}
