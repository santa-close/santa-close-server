package com.santaclose.app.sample.resolver

import arrow.core.left
import arrow.core.right
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.sample.resolver.dto.SampleAppDetail
import com.santaclose.app.sample.service.SampleAppQueryService
import com.santaclose.app.util.query
import com.santaclose.app.util.success
import com.santaclose.app.util.verify
import com.santaclose.app.util.verifyError
import com.santaclose.lib.entity.sample.type.SampleStatus
import io.mockk.every
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
internal class SampleAppQueryResolverTest @Autowired constructor(
    private val webTestClient: WebTestClient,
    @MockkBean
    private val sampleAppQueryService: SampleAppQueryService,
) {
    @Nested
    inner class Sample {
        @Test
        fun `데이터가 없는 경우 에러가 발생한다`() {
            // given
            @Language("GraphQL")
            val query = """query { 
            sample(input: {price: 123}) {
                name
                price
                status
            }}"""
            every { sampleAppQueryService.findByPrice(123) } returns Error("error").left()

            // when
            val response = webTestClient.query(query)

            // then
            response.verifyError("Exception while fetching data (/sample) : 가져오기 실패")
        }

        @Test
        fun `데이터가 있는 경우 sample 을 가져온다`() {
            // given
            @Language("GraphQL")
            val query = """query { 
            sample(input: {price: 123}) {
                name
                price
                status
            }}"""

            val dto = SampleAppDetail("name", 1000, SampleStatus.OPEN).right()
            every { sampleAppQueryService.findByPrice(123) } returns dto

            // when
            val response = webTestClient.query(query)

            // then
            response.success("sample").apply {
                verify("sample.name", "name")
                verify("sample.price", 1000)
                verify("sample.status", SampleStatus.OPEN.name)
            }
        }
    }
}
