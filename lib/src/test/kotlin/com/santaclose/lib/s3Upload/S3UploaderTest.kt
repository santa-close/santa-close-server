package com.santaclose.lib.s3Upload

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.runtime.endpoint.AwsEndpoint
import aws.sdk.kotlin.runtime.endpoint.StaticEndpointResolver
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.smithy.kotlin.runtime.auth.awscredentials.Credentials
import aws.smithy.kotlin.runtime.content.ByteStream
import io.kotest.assertions.throwables.shouldNotThrow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
internal class S3UploaderTest {
  companion object {
    @Container
    private val localStack =
      LocalStackContainer(DockerImageName.parse("localstack/localstack:0.14.0")).apply {
        withServices(LocalStackContainer.Service.S3)
      }
    lateinit var s3Uploader: S3Uploader
    lateinit var s3Client: S3Client

    @BeforeAll
    @JvmStatic
    fun beforeAll() {
      s3Client =
        S3Client {
          region = localStack.region
          endpointResolver =
            StaticEndpointResolver(
              AwsEndpoint(
                url = localStack.getEndpointOverride(LocalStackContainer.Service.S3).toString()
              )
            )
          credentialsProvider =
            StaticCredentialsProvider(Credentials(localStack.accessKey, localStack.secretKey))
        }
      runBlocking { s3Client.createBucket { bucket = "test" } }
      s3Uploader = S3Uploader.createByClient(s3Client)
    }
  }

  @Nested
  inner class Upload {

    @Test
    fun `파일 업로드를 수행한다`() {
      runBlocking {
        // given
        val file = MockMultipartFile("fileName", "file content".toByteArray())

        // when
        s3Uploader.upload("test", "path", ByteStream.fromBytes(file.bytes), file.contentType ?: "")

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
