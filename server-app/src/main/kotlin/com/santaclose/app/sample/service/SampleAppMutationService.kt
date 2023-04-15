package com.santaclose.app.sample.service

import arrow.core.Either
import com.santaclose.app.sample.repository.SampleAppRepository
import com.santaclose.lib.entity.sample.Sample
import com.santaclose.lib.web.exception.DomainError.DBFailure
import com.santaclose.lib.web.exception.catchDB
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SampleAppMutationService(
    private val sampleAppRepository: SampleAppRepository,
) {

    @Transactional
    fun create(sample: Sample): Either<DBFailure, Unit> =
        Either.catchDB { sampleAppRepository.save(sample) }
}
