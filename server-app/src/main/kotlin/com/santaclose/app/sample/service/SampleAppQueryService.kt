package com.santaclose.app.sample.service

import arrow.core.Either
import com.santaclose.app.sample.controller.dto.SampleAppDetail
import com.santaclose.app.sample.repository.SampleAppQueryRepository
import com.santaclose.lib.web.exception.DomainError.DBFailure
import org.springframework.stereotype.Service

@Service
class SampleAppQueryService(
    private val sampleAppRepository: SampleAppQueryRepository,
) {
    fun findByPrice(price: Int): Either<DBFailure, SampleAppDetail> =
        sampleAppRepository.findByPrice(price)
}
