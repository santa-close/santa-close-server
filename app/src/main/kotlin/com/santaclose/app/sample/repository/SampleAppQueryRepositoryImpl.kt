package com.santaclose.app.sample.repository

import arrow.core.Either
import arrow.core.Either.Companion.catch
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.santaclose.app.sample.resolver.dto.SampleDto
import com.santaclose.lib.entity.sample.Sample
import org.springframework.stereotype.Repository

@Repository
class SampleAppQueryRepositoryImpl(
    private val springDataQueryFactory: SpringDataQueryFactory,
) : SampleAppQueryRepository {

    override fun findByPrice(price: Int): Either<Throwable, SampleDto> = catch {
        springDataQueryFactory.singleQuery {
            selectMulti(col(Sample::name), col(Sample::price), col(Sample::status))
            from(Sample::class)
            where(col(Sample::price).equal(price))
        }
    }
}
