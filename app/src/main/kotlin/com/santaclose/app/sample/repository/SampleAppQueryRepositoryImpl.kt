package com.santaclose.app.sample.repository

import arrow.core.Option
import arrow.core.toOption
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
    override fun findByPrice(price: Int): Option<SampleDto> {
        return springDataQueryFactory.singleQuery<SampleDto> {
            selectMulti(col(Sample::name), col(Sample::price), col(Sample::status))
            from(Sample::class)
            where(col(Sample::price).equal(price))
        }.toOption()
    }
}
