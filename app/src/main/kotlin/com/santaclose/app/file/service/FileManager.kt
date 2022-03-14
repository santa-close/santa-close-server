package com.santaclose.app.file.service

import aws.smithy.kotlin.runtime.content.ByteStream
import com.santaclose.app.config.S3Config
import com.santaclose.lib.s3Upload.S3Uploader
import org.springframework.stereotype.Service

@Service
class FileManager(
    private val s3Config: S3Config,
) {
    private val s3Uploader =
        S3Uploader.create(
            s3Config.endPoint,
            s3Config.region,
            s3Config.credentialsAccessKey,
            s3Config.credentialsSecretKey
        )

    suspend fun upload(path: String, data: ByteStream, contentType: String) =
        s3Uploader.upload(s3Config.bucket, path, data, contentType)
}
