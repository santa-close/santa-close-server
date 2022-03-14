package com.santaclose.app.sample.service

import arrow.core.Either
import com.santaclose.app.sample.repository.SampleAppQueryRepository
import com.santaclose.app.sample.resolver.dto.SampleAppDetail
import org.springframework.stereotype.Service

@Service
class SampleAppQueryService(
  private val sampleAppRepository: SampleAppQueryRepository,
) {
  fun findByPrice(price: Int): Either<Throwable, SampleAppDetail> =
    sampleAppRepository.findByPrice(price)
}
