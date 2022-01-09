package com.santaclose.app.sample.repository

import arrow.core.Option
import arrow.core.toOption
import com.santaclose.app.sample.resolver.dto.QSampleDto
import com.santaclose.app.sample.resolver.dto.SampleDto
import com.santaclose.lib.entity.sample.QSample.sample
import com.santaclose.lib.entity.sample.Sample
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class SampleAppQueryRepositoryImpl : QuerydslRepositorySupport(Sample::class.java), SampleAppQueryRepository {
    override fun findByPrice(price: Int): Option<SampleDto> {
        return from(sample)
            .where(sample.price.eq(price))
            .select(QSampleDto(sample.name, sample.price, sample.status))
            .fetchOne()
            .toOption()
    }
}
