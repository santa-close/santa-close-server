package com.santaclose.app.sample.repository

import arrow.core.Option
import com.santaclose.entity.QSample
import com.santaclose.entity.Sample
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class SampleAppQueryRepositoryImpl : QuerydslRepositorySupport(Sample::class.java), SampleAppQueryRepository {
    override fun findByPrice(price: Int): Option<Sample> {
        return from(sample)
            .where(sample.price.eq(price))
            .fetchOne()
            .let { Option.fromNullable(it) }
    }

    companion object {
        private val sample: QSample = QSample.sample
    }
}
