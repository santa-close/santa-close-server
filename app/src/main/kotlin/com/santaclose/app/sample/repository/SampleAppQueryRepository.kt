package com.santaclose.app.sample.repository

import arrow.core.Option
import com.santaclose.app.sample.resolver.dto.SampleDto

interface SampleAppQueryRepository {
    fun findByPrice(price: Int): Option<SampleDto>
}
