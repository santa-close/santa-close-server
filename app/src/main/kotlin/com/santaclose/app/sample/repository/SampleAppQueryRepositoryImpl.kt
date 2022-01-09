package com.santaclose.app.sample.repository

import arrow.core.Option
import com.santaclose.lib.entity.sample.QSample.sample
import com.santaclose.lib.entity.sample.Sample
import com.santaclose.lib.extension.fetchOption
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class SampleAppQueryRepositoryImpl : QuerydslRepositorySupport(Sample::class.java), SampleAppQueryRepository {
    override fun findByPrice(price: Int): Option<Sample> {
        return from(sample)
            .where(sample.price.eq(price))
            .fetchOption()
    }
}
