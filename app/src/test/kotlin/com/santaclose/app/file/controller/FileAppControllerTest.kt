package com.santaclose.app.file.controller

import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.file.service.FileAppService
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
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

  @Nested
  inner class UploadImage {
    @Test
    fun `파일 업로드시 url을 반환한다`() {
      // given
      val bodyBuilder = MultipartBodyBuilder()
      bodyBuilder
        .part("file", "text".toByteArray())
        .header("Content-Disposition", "form-data; name=profileImage; filename=profile-image.jpg")

      //      coEvery { fileAppService.uploadImage(any()) } returns
      // "http://localhost/image.png".right()

      // when
      val response =
        webTestClient
          .post()
          .uri("/api/image")
          .contentType(MediaType.MULTIPART_FORM_DATA)
          .body(BodyInserters.fromMultipartData("file", bodyBuilder.build()))
          .exchange()

      // then
      // response.expectBody().isEmpty
    }
  }
}
