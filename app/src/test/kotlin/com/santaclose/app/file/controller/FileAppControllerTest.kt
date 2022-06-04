package com.santaclose.app.file.controller

import arrow.core.left
import arrow.core.right
import arrow.core.some
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.auth.context.AppSession
import com.santaclose.app.auth.context.parser.ServerRequestParser
import com.santaclose.app.file.service.FileAppService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import io.mockk.coEvery
import io.mockk.every
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpStatus
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@SpringBootTest
@AutoConfigureWebTestClient
internal class FileAppControllerTest
@Autowired
constructor(
    private val webTestClient: WebTestClient,
) {
    @MockkBean private lateinit var fileAppService: FileAppService
    @MockkBean private lateinit var serverRequestParser: ServerRequestParser

    @Nested
    inner class UploadImage {
        @Test
        fun `유저 권한이 없을시 401 에러를 발생한다`() {
            // given
            val bodyBuilder = MultipartBodyBuilder()
            val resource = FileSystemResource("src/test/resources/application.yml")
            bodyBuilder.part("file", resource)

            // when
            val response =
                webTestClient
                    .post()
                    .uri("/api/image")
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .exchange()

            // then
            response.expectStatus().isUnauthorized
        }

        @Test
        fun `파일 업로드 에러시 500 에러를 발생한다`() {
            // given
            val bodyBuilder = MultipartBodyBuilder()
            val resource = FileSystemResource("src/test/resources/application.yml")
            bodyBuilder.part("file", resource)
            val token = "token"
            every { serverRequestParser.parseJwt(token) } returns AppSession(1, AppUserRole.USER).some()
            coEvery { fileAppService.uploadImage(any()) } returns Exception("error").left()

            // when
            val response =
                webTestClient
                    .post()
                    .uri("/api/image")
                    .header("Authorization", "Bearer $token")
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .exchange()

            // then
            response.expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
        }

        @Test
        fun `이미지 파일에 대한 url을 반환한다`() {
            // given
            val bodyBuilder = MultipartBodyBuilder()
            val resource = FileSystemResource("src/test/resources/application.yml")
            bodyBuilder.part("file", resource)
            val token = "token"
            every { serverRequestParser.parseJwt(token) } returns AppSession(1, AppUserRole.USER).some()
            val imageUrl = "http://localhost/image.png"
            coEvery { fileAppService.uploadImage(any()) } returns imageUrl.right()

            // when
            val response =
                webTestClient
                    .post()
                    .uri("/api/image")
                    .header("Authorization", "Bearer $token")
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .exchange()

            // then
            response.expectBody().jsonPath("$.url").isEqualTo(imageUrl)
        }
    }
}
