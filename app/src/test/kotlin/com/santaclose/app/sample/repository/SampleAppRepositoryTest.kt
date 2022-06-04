package com.santaclose.app.sample.repository

import com.santaclose.app.sample.repository.dto.SampleNamePriceDto
import com.santaclose.lib.entity.sample.Sample
import com.santaclose.lib.entity.sample.type.SampleStatus
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class SampleAppRepositoryTest
@Autowired
constructor(
    private val sampleAppRepository: SampleAppRepository,
) {
    @Nested
    inner class FindById {
        @Test
        fun `정상적으로 데이터를 가져온다`() {
            // given
            val sample = Sample(name = "name", price = 123, status = SampleStatus.CLOSE)
            sampleAppRepository.save(sample)

            // when
            val result = sampleAppRepository.findById(sample.id).orElse(null)

            // then
            result shouldBe sample
        }
    }

    @Nested
    inner class FindByIdIn {
        @Test
        fun `id 에 해당하는 데이터를 dto 에 담는다`() {
            // given
            val sample = Sample(name = "name", price = 123, status = SampleStatus.CLOSE)
            sampleAppRepository.save(sample)

            // when
            val result = sampleAppRepository.findByIdIn(listOf(sample.id))

            // then
            result shouldBe listOf(SampleNamePriceDto(sample.name, sample.price))
        }
    }
}
