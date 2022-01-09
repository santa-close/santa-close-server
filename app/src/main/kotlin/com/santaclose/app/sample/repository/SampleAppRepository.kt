package com.santaclose.app.sample.repository

import arrow.core.Option
import com.santaclose.lib.entity.sample.Sample
import org.springframework.data.jpa.repository.JpaRepository

interface SampleAppRepository : JpaRepository<Sample, Long>, SampleAppQueryRepository

interface SampleAppQueryRepository {
    fun findByPrice(price: Int): Option<Sample>
}
