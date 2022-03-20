package com.santaclose.app.file.controller

import com.santaclose.app.file.controller.dto.UploadImageResponse
import com.santaclose.app.file.service.FileAppService
import com.santaclose.lib.web.error.getOrThrow
import com.santaclose.lib.web.req.UploadImageRequest
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/image")
class FileAppController constructor(private val fileAppService: FileAppService) {

  @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
  suspend fun uploadImage(@RequestBody file: Mono<FilePart>): UploadImageResponse =
    fileAppService
      .uploadImage(UploadImageRequest(file.block() as MultipartFile))
      .map { UploadImageResponse(it) }
      .getOrThrow()
}
