package com.santaclose.app.sample.repository

import com.santaclose.app.sample.controller.dto.SampleAppDetail
import com.santaclose.app.util.createQueryFactory
import com.santaclose.lib.entity.sample.Sample
import com.santaclose.lib.entity.sample.type.SampleStatus
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager
import javax.validation.ConstraintViolationException

@DataJpaTest
internal class SampleAppQueryRepositoryImplTest @Autowired constructor(
    private val em: EntityManager,
) {
    private val sampleAppQueryRepository = SampleAppQueryRepositoryImpl(em.createQueryFactory())

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
            result shouldBeRight sample.run { SampleAppDetail(name, price, status) }
        }

        @Test
        fun `bean validation 이 정상적으로 동작한다`() {
            val exception =
                shouldThrow<ConstraintViolationException> {
                    val sample = Sample("test", -123, SampleStatus.OPEN)
                    em.persist(sample)
                }

            exception.message shouldContain "javax.validation.constraints.Positive"
        }
    }
}
