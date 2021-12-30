package com.santaclose.app.sample.repository

import com.santaclose.entity.Sample
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@EntityScan(basePackages = ["com.santaclose.entity"])
class SampleAppRepositoryTest @Autowired constructor(
    val sampleAppRepository: SampleAppRepository,
) {

    @Test
    fun `정상적으로 데이터를 생성한다`() {
        // given
        val sample = Sample(name = "name", price = 123)

        // when
        sampleAppRepository.save(sample)

        // then
        val count = sampleAppRepository.count()
        count shouldBe 1
    }
}
