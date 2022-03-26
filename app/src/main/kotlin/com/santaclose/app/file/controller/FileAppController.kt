package com.santaclose.app.file.controller

import com.santaclose.app.file.controller.dto.UploadImageResponse
import com.santaclose.app.file.service.FileAppService
import com.santaclose.lib.web.error.getOrThrow
import com.santaclose.lib.web.req.UploadImageRequest
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class FileAppController constructor(private val fileAppService: FileAppService) {

  @PostMapping("/image")
  suspend fun uploadImage(@RequestPart file: FilePart): UploadImageResponse =
    fileAppService
      .uploadImage(UploadImageRequest(file))
      .map { UploadImageResponse(it) }
      .getOrThrow()
}
