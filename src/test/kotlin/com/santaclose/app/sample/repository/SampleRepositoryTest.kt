package com.santaclose.app.sample.repository

import com.santaclose.app.api.sample.repository.SampleRepository
import com.santaclose.app.entity.sample.Sample
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class SampleRepositoryTest @Autowired constructor(
    val sampleRepository: SampleRepository,
) {

    @Test
    fun `정상적으로 데이터를 생성한다`() {
        // given
        val sample = Sample(name = "name", price = 123)

        // when
        sampleRepository.save(sample)

        // then
        val count = sampleRepository.count()
        count shouldBe 1
    }
}
