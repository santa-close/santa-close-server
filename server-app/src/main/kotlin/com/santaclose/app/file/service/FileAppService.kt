package com.santaclose.app.file.service

import arrow.core.Either
import arrow.core.flatMap
import com.santaclose.lib.web.req.UploadImageRequest
import org.springframework.stereotype.Service

@Service
class FileAppService(
    private val fileManager: FileManager,
) {

    fun uploadImage(request: UploadImageRequest): Either<Throwable, String> =
        request
            .validateFile()
            .flatMap { fileManager.upload(request.path, request.fileData, request.contentType) }
}
