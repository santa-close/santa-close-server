package com.santaclose.app.sample.service

import arrow.core.toOption
import com.navercorp.fixturemonkey.kotlin.KFixtureMonkey
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.sample.repository.SampleAppRepository
import com.santaclose.app.sample.resolver.dto.SampleDto
import io.kotest.assertions.arrow.core.shouldBeSome
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [SampleAppService::class])
internal class SampleAppServiceTest @Autowired constructor(
    private val sampleAppService: SampleAppService,
    @MockkBean
    private val sampleAppRepository: SampleAppRepository
) {
    private val sut = KFixtureMonkey.create()

    @Test
    fun `정상적으로 데이터를 가져온다`() {
        // given
        val sampleDto = sut.giveMeOne(SampleDto::class.java)
        every { sampleAppRepository.findByPrice(sampleDto.price) } returns sampleDto.toOption()

        // when
        val findByPrice = sampleAppService.findByPrice(sampleDto.price)

        // then
        findByPrice shouldBeSome sampleDto
        verify { sampleAppRepository.findByPrice(sampleDto.price) }
    }
}
