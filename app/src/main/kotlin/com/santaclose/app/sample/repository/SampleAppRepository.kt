package com.santaclose.app.sample.repository

import arrow.core.Option
import com.santaclose.app.sample.resolver.dto.SampleDto
import com.santaclose.lib.entity.sample.Sample
import org.springframework.data.jpa.repository.JpaRepository

interface SampleAppRepository : JpaRepository<Sample, Long>, SampleAppQueryRepository

interface SampleAppQueryRepository {
    fun findByPrice(price: Int): Option<SampleDto>
}
