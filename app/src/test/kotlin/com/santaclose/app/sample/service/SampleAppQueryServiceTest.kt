package com.santaclose.app.sample.service

import arrow.core.right
import com.navercorp.fixturemonkey.kotlin.KFixtureMonkey
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.sample.repository.SampleAppQueryRepository
import com.santaclose.app.sample.resolver.dto.SampleDto
import io.kotest.assertions.arrow.core.shouldBeRight
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [SampleAppQueryService::class])
internal class SampleAppQueryServiceTest @Autowired constructor(
    private val sampleAppQueryService: SampleAppQueryService,
    @MockkBean
    private val sampleAppQueryRepository: SampleAppQueryRepository,
) {
    private val sut = KFixtureMonkey.create()

    @Test
    fun `정상적으로 데이터를 가져온다`() {
        // given
        val sampleDto = sut.giveMeOne(SampleDto::class.java)
        every { sampleAppQueryRepository.findByPrice(sampleDto.price) } returns sampleDto.right()

        // when
        val result = sampleAppQueryService.findByPrice(sampleDto.price)

        // then
        result shouldBeRight sampleDto
    }
}
