package com.santaclose.lib.s3Upload

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import io.kotest.matchers.shouldBe
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
            LocalStackContainer(DockerImageName.parse("localstack/localstack")).apply {
                withServices(LocalStackContainer.Service.S3)
            }
        lateinit var s3Uploader: S3Uploader
        lateinit var s3: AmazonS3

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            s3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(
                    AwsClientBuilder.EndpointConfiguration(
                        localStack.getEndpointOverride(LocalStackContainer.Service.S3).toString(),
                        localStack.region,
                    ),
                )
                .withCredentials(
                    AWSStaticCredentialsProvider(BasicAWSCredentials(localStack.accessKey, localStack.secretKey)),
                )
                .build()
            s3.createBucket("test")
            s3Uploader = S3Uploader.createByClient(s3)
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
                s3Uploader.upload("test", "path", file.inputStream, file.contentType ?: "")

                // then
                val result = s3.getObject("test", "path")
                result.key shouldBe "path"
            }
        }
    }
}
