package com.santaclose.app.sample.service

import arrow.core.right
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import com.santaclose.app.sample.controller.dto.SampleAppDetail
import com.santaclose.app.sample.repository.SampleAppQueryRepository
import io.kotest.assertions.arrow.core.shouldBeRight
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class SampleAppQueryServiceTest {
    @InjectMockKs
    private lateinit var sampleAppQueryService: SampleAppQueryService

    @MockK
    private lateinit var sampleAppQueryRepository: SampleAppQueryRepository

    private val sut = FixtureMonkey.builder()
        .plugin(KotlinPlugin())
        .build()

    @Test
    fun `정상적으로 데이터를 가져온다`() {
        // given
        val sampleAppDetail = sut.giveMeOne<SampleAppDetail>()
        every { sampleAppQueryRepository.findByPrice(sampleAppDetail.price) } returns
            sampleAppDetail.right()

        // when
        val result = sampleAppQueryService.findByPrice(sampleAppDetail.price)

        // then
        result shouldBeRight sampleAppDetail
    }
}
