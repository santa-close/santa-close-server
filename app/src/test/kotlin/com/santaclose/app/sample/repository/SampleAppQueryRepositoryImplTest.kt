package com.santaclose.app.sample.repository

import com.santaclose.app.sample.resolver.dto.SampleDto
import com.santaclose.app.util.TestQueryFactory
import com.santaclose.lib.entity.sample.Sample
import com.santaclose.lib.entity.sample.type.SampleStatus
import io.kotest.assertions.arrow.core.shouldBeSome
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class SampleAppQueryRepositoryImplTest : TestQueryFactory() {
    private val sampleAppQueryRepository by lazy {
        SampleAppQueryRepositoryImpl(queryFactory)
    }

    @Test
    fun `정상적으로 데이터를 가져온다`() {
        // given
        val sample = Sample("name", 123, SampleStatus.OPEN)
        em.persist(sample)

        // when
        val result = sampleAppQueryRepository.findByPrice(123)

        // then
        result shouldBeSome sample.run {
            SampleDto(name, price, status)
        }
    }
}
