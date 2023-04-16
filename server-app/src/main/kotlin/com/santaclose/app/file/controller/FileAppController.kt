package com.santaclose.app.file.controller

import com.santaclose.app.auth.security.UserAuthorize
import com.santaclose.app.file.controller.dto.UploadImageResponse
import com.santaclose.app.file.service.FileAppService
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.req.UploadImageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/api")
class FileAppController(
    private val fileAppService: FileAppService,
) {
    private val logger = logger()

    @PostMapping("/image")
    @UserAuthorize
    fun uploadImage(@RequestPart file: FilePart): Mono<Any> =
        fileAppService.uploadImage(UploadImageRequest(file))
            .map { UploadImageResponse(it) }
            .onLeft { logger.error(it.message, it) }
            .fold(
                ifLeft = { ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).toMono() },
                ifRight = { it.toMono() },
            )
}
