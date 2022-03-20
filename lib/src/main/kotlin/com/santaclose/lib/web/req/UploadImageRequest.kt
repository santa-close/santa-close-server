package com.santaclose.lib.web.req

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import aws.smithy.kotlin.runtime.content.ByteStream
import java.time.LocalDate
import java.util.*
import org.springframework.web.multipart.MultipartFile

data class UploadImageRequest(
  val file: MultipartFile,
  var date: LocalDate = LocalDate.now(),
  var code: UUID = UUID.randomUUID(),
) {
  companion object {
    private val imageExtensions = arrayOf("jpg", "png", "jpeg")
    private val imageMimeTypes = arrayOf("image/jpg", "image/png", "image/jpeg")
  }

  private val fileName: String
    get() = file.originalFilename ?: ""

  val fileData: ByteStream
    get() = ByteStream.fromBytes(file.bytes)

  val contentType: String
    get() = file.contentType ?: ""

  val path: String
    get() = "image/$date/$code-$fileName"

  fun validateFile(): Either<IllegalArgumentException, Unit> {

    if (!imageMimeTypes.contains(contentType.lowercase())) {
      return IllegalArgumentException("${imageExtensions.joinToString(",")} 파일만 업로드 가능합니다.").left()
    }

    val extension = this.fileName.split('.').last()
    if (!imageExtensions.contains(extension)) {
      return IllegalArgumentException("${imageExtensions.joinToString(",")} 파일만 업로드 가능합니다.").left()
    }

    return Unit.right()
  }
}
