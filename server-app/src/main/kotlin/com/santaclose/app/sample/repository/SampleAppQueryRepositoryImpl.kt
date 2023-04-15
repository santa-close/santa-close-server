package com.santaclose.app.sample.repository

import arrow.core.Either
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.santaclose.app.sample.controller.dto.SampleAppDetail
import com.santaclose.lib.entity.sample.Sample
import com.santaclose.lib.web.exception.DomainError.DBFailure
import com.santaclose.lib.web.exception.catchDB
import org.springframework.stereotype.Repository

@Repository
class SampleAppQueryRepositoryImpl(
    private val springDataQueryFactory: SpringDataQueryFactory,
) : SampleAppQueryRepository {

    override fun findByPrice(price: Int): Either<DBFailure, SampleAppDetail> = Either.catchDB {
        springDataQueryFactory.singleQuery {
            selectMulti(col(Sample::name), col(Sample::price), col(Sample::status))
            from(Sample::class)
            where(col(Sample::price).equal(price))
        }
    }
}
