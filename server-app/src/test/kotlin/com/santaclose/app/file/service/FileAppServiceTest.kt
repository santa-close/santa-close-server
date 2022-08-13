package com.santaclose.app.file.service

import arrow.core.left
import arrow.core.right
import com.santaclose.lib.web.req.UploadImageRequest
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.FreeSpec
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import reactor.kotlin.core.publisher.toMono
import java.io.InputStream

internal class FileAppServiceTest : FreeSpec(
    {
        val fileManager = mockk<FileManager>()
        val fileAppService = FileAppService(fileManager)

        "UploadImage" - {
            "파일 유효성 검사 실패시 left를 반환한다" {
                // given
                val request = mockk<UploadImageRequest>(relaxed = true)
                val exception = IllegalArgumentException()
                every { request.fileData } returns InputStream.nullInputStream().toMono()
                every { request.validateFile() } returns exception.left()

                // when
                val result = fileAppService.uploadImage(request)

                // then
                result shouldBeLeft exception
            }

            "파일 업로드 실패 시 left를 반환한다" {
                // given
                val request = mockk<UploadImageRequest>(relaxed = true)
                every { request.fileData } returns InputStream.nullInputStream().toMono()
                every { request.validateFile() } returns Unit.right()

                val exception = Exception("file upload error")
                coEvery { fileManager.upload(request.path, any(), request.contentType) } returns
                    exception.left()

                // when
                val result = fileAppService.uploadImage(request)

                // then
                result shouldBeLeft exception
            }

            "정상적으로 파일이 업로드된다" {
                // given
                val request = mockk<UploadImageRequest>(relaxed = true)
                every { request.fileData } returns InputStream.nullInputStream().toMono()
                every { request.validateFile() } returns Unit.right()

                val url = "http://localhost/image.png"
                coEvery { fileManager.upload(request.path, any(), request.contentType) } returns url.right()

                // when
                val result = fileAppService.uploadImage(request)

                // then
                result shouldBeRight url
            }
        }
    },
)
