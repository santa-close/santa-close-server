package com.santaclose.app.file.service

import arrow.core.left
import arrow.core.right
import aws.sdk.kotlin.services.s3.model.PutObjectResponse
import com.santaclose.lib.web.req.UploadImageRequest
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.string.shouldContain
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile

internal class FileAppServiceTest {
    private val fileManager = mockk<FileManager>()
    private val fileAppService = FileAppService(fileManager)

    @Nested
    inner class UploadImage {
        @Test
        fun `유효성 검사 실패시 left를 반환한다`() = runTest {
            // given
            val file = MockMultipartFile("image.invalid", "".toByteArray())
            val request = UploadImageRequest(file)

            // when
            val result = fileAppService.uploadImage(request)

            // then
            result.shouldBeLeft().apply {
                message shouldContain "파일만 업로드 가능합니다."
            }
        }

        @Test
        fun `파일 업로드 실패 시 left를 반환한다`() = runTest {
            // given
            val file = MockMultipartFile("image.png", "image.png", "image/jpg", "".toByteArray())
            val request = UploadImageRequest(file)
            val exception = Exception("file upload error")
            coEvery { fileManager.upload(request.path, any(), request.contentType) } returns exception.left()

            // when
            val result = fileAppService.uploadImage(request)

            // then
            result shouldBeLeft exception
        }

        @Test
        fun `정상적으로 파일이 업로드된다`() = runTest {
            // given
            val file = MockMultipartFile("image.png", "image.png", "image/jpg", "".toByteArray())
            val request = UploadImageRequest(file)
            val mockResponse = mockk<PutObjectResponse>()
            coEvery { fileManager.upload(request.path, any(), request.contentType) } returns mockResponse.right()

            // when
            val result = fileAppService.uploadImage(request)

            // then
            result.shouldBeRight()
        }
    }
}