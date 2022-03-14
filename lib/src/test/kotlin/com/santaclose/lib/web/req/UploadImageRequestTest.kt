package com.santaclose.lib.web.req

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import java.time.LocalDate

internal class UploadImageRequestTest {

  @Test
  fun `path를 생성한다`() {
    // given
    val file = MockMultipartFile("image.png", "image.png", "application/pdf", "".toByteArray())
    val givenDate = LocalDate.now()

    // when
    val request = UploadImageRequest(file, givenDate)

    // then
    request.path shouldContain "image/${givenDate}"
    request.path shouldContain file.originalFilename
  }

  @Nested
  inner class ValidateFile {
    @Test
    fun `유효하지 않은 contentType 일시 에러를 발생한다`() {
      // given
      val file = MockMultipartFile("image.png", "image.png", "application/pdf", "".toByteArray())
      val request = UploadImageRequest(file)

      // when
      val result = request.validateFile()

      // then
      result.shouldBeLeft().apply {
        message shouldContain "파일만 업로드 가능합니다."
      }
    }

    @Test
    fun `유효하지 않은 확장자 일시 에러를 발생한다`() {
      // given
      val file = MockMultipartFile("image.pdf", "image.pdf", "", "".toByteArray())
      val request = UploadImageRequest(file)

      // when
      val result = request.validateFile()

      // then
      result.shouldBeLeft().apply {
        message shouldContain "파일만 업로드 가능합니다."
      }
    }
  }
}
