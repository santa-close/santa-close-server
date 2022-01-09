package com.santaclose.app.sample.repository

import com.santaclose.lib.entity.sample.Sample
import io.kotest.assertions.arrow.core.shouldBeSome
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class SampleAppRepositoryTest @Autowired constructor(
    val sampleAppRepository: SampleAppRepository
) {
    @Test
    fun `정상적으로 데이터를 생성한다`() {
        // given
        val sample = Sample(name = "name", price = 123)

        // when
        sampleAppRepository.save(sample)

        // then
        val findSample = sampleAppRepository.findByPrice(sample.price)
        findSample shouldBeSome sample
    }
}
