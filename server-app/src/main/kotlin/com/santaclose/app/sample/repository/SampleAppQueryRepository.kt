package com.santaclose.app.sample.repository

import arrow.core.Either
import com.santaclose.app.sample.controller.dto.SampleAppDetail
import com.santaclose.lib.web.exception.DomainError.DBFailure

interface SampleAppQueryRepository {
    fun findByPrice(price: Int): Either<DBFailure, SampleAppDetail>
}
