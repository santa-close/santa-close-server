package com.santaclose.app.sample.resolver

import arrow.core.left
import arrow.core.right
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.sample.resolver.dto.SampleAppDetail
import com.santaclose.app.sample.service.SampleAppQueryService
import com.santaclose.app.util.AppContextMocker
import com.santaclose.app.util.QueryInput
import com.santaclose.app.util.query
import com.santaclose.app.util.withError
import com.santaclose.app.util.withSuccess
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.entity.sample.type.SampleStatus
import com.santaclose.lib.web.error.GraphqlErrorCode
import io.mockk.every
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import javax.persistence.NoResultException

@SpringBootTest
@AutoConfigureWebTestClient
internal class SampleAppQueryResolverTest @Autowired constructor(
    private val webTestClient: WebTestClient,
    @MockkBean
    private val sampleAppQueryService: SampleAppQueryService,
) : AppContextMocker() {
    @Nested
    inner class Sample {
        @Test
        fun `권한없는 유저가 요청 시 에러가 발생한다`() {
            // given
            val query = QueryInput(
                """query {
                |  sample(input: {price: 123}) {
                |    name
                |    price
                |    status
                |  }
                |}
                """.trimMargin()
            )
            every { sampleAppQueryService.findByPrice(123) } returns NoResultException("no result").left()
            withAnonymousUser()

            // when
            val response = webTestClient.query(query)

            // then
            response.withError(GraphqlErrorCode.UNAUTHORIZED, "접근 권한이 없습니다")
        }

        @Test
        fun `데이터가 없는 경우 에러가 발생한다`() {
            // given
            val query = QueryInput(
                """query {
                |  sample(input: {price: 123}) {
                |    name
                |    price
                |    status
                |  }
                |}
                """.trimMargin()
            )
            every { sampleAppQueryService.findByPrice(123) } returns NoResultException("no result").left()
            withMockUser(AppUserRole.USER)

            // when
            val response = webTestClient.query(query)

            // then
            response.withError(GraphqlErrorCode.NOT_FOUND, "no result")
        }

        @Test
        fun `데이터가 있는 경우 sample 을 가져온다`() {
            // given
            val query = QueryInput(
                """query {
                |  sample(input: {price: 123}) {
                |    name
                |    price
                |    status
                |  }
                |}
                """.trimMargin()
            )
            val dto = SampleAppDetail("name", 1000, SampleStatus.OPEN).right()
            every { sampleAppQueryService.findByPrice(123) } returns dto
            withMockUser(AppUserRole.USER)

            // when
            val response = webTestClient.query(query)

            // then
            response.withSuccess("sample") {
                expect("name").isEqualTo("name")
                expect("price").isEqualTo(1000)
                expect("status").isEqualTo(SampleStatus.OPEN.name)
            }
        }
    }
}
