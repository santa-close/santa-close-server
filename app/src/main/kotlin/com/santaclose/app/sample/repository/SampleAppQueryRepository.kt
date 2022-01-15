package com.santaclose.app.sample.repository

import arrow.core.Either
import com.santaclose.app.sample.resolver.dto.SampleDto

interface SampleAppQueryRepository {
    fun findByPrice(price: Int): Either<Throwable, SampleDto>
}
