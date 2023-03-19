package com.santaclose.app.file.service

import arrow.core.Either
import com.santaclose.app.config.S3Config
import com.santaclose.lib.s3Upload.S3Uploader
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class FileManager(
    private val s3Config: S3Config,
) {
    private val s3Uploader =
        S3Uploader.create(
            s3Config.endPoint,
            s3Config.region,
            s3Config.credentialsAccessKey,
            s3Config.credentialsSecretKey,
        )

    fun upload(
        path: String,
        data: InputStream,
        contentType: String,
    ): Either<Throwable, String> =
        s3Uploader
            .upload(s3Config.bucket, path, data, contentType)
            .map { "${s3Config.domain}/$path" }
}
