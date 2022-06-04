package com.santaclose.app.file.controller

import arrow.core.flatMap
import arrow.core.lastOrNone
import arrow.core.toOption
import com.santaclose.app.file.controller.dto.UploadImageResponse
import com.santaclose.app.file.service.FileAppService
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.HttpException.UnauthorizedException
import com.santaclose.lib.web.req.UploadImageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class FileAppController
constructor(
    private val fileAppService: FileAppService,
) {
    private val logger = logger()

    @PostMapping("/image")
    suspend fun uploadImage(
        @RequestPart file: FilePart,
        @RequestHeader("Authorization") token: String?
    ): ResponseEntity<Any> =
        token
            .toOption()
            .flatMap { it.split(" ").lastOrNone() }
            .toEither { UnauthorizedException("접근 권한이 없습니다.") }
            .flatMap { fileAppService.uploadImage(UploadImageRequest(file)) }
            .map { UploadImageResponse(it) }
            .tapLeft { logger.error(it.message, it) }
            .fold(
                {
                    when (it) {
                        is UnauthorizedException -> ResponseEntity(
                            "접근 권한이 없습니다.",
                            HttpStatus.UNAUTHORIZED
                        )
                        else -> ResponseEntity("서버 에러", HttpStatus.INTERNAL_SERVER_ERROR)
                    }
                },
                { ResponseEntity(it, HttpStatus.OK) }
            )
}
