package com.santaclose.app.sample.repository

import com.santaclose.app.sample.resolver.dto.SampleAppDetail
import com.santaclose.app.util.TestQueryFactory
import com.santaclose.lib.entity.sample.Sample
import com.santaclose.lib.entity.sample.type.SampleStatus
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.validation.ConstraintViolationException

@DataJpaTest
internal class SampleAppQueryRepositoryImplTest : TestQueryFactory() {
    private val sampleAppQueryRepository by lazy {
        SampleAppQueryRepositoryImpl(queryFactory)
    }

    @Nested
    inner class FindByPrice {
        @Test
        fun `정상적으로 데이터를 가져온다`() {
            // given
            val sample = Sample("name", 123, SampleStatus.OPEN)
            em.persist(sample)

            // when
            val result = sampleAppQueryRepository.findByPrice(123)

            // then
            result shouldBeRight sample.run {
                SampleAppDetail(name, price, status)
            }
        }

        @Test
        fun `bean validation 이 정상적으로 동작한다`() {
            val exception = shouldThrow<ConstraintViolationException> {
                val sample = Sample("test", -123, SampleStatus.OPEN)
                em.persist(sample)
            }

            exception.message shouldContain "0보다 커야 합니다"
        }
    }
}
