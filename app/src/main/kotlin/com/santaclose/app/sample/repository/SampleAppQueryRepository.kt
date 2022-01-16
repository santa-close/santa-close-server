package com.santaclose.app.sample.repository

import arrow.core.Either
import com.santaclose.app.sample.resolver.dto.SampleAppDetail

interface SampleAppQueryRepository {
    fun findByPrice(price: Int): Either<Throwable, SampleAppDetail>
}
