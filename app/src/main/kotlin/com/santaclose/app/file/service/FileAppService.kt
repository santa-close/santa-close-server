package com.santaclose.app.file.service

import arrow.core.Either
import arrow.core.flatMap
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.req.UploadImageRequest
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class FileAppService(
  private val fileManager: FileManager,
) {
  private val logger = logger()

  suspend fun uploadImage(request: UploadImageRequest): Either<Throwable, String> =
    request
      .validateFile()
      .flatMap {
        fileManager.upload(request.path, request.fileData.awaitSingle(), request.contentType)
      }
      .tapLeft { logger.error(it.message, it) }
}
