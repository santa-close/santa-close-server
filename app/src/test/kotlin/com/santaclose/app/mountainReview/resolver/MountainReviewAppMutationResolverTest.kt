package com.santaclose.app.mountainReview.resolver

import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.mountainReview.service.MountainReviewAppMutationService
import com.santaclose.app.util.QueryInput
import com.santaclose.app.util.query
import com.santaclose.app.util.withError
import com.santaclose.app.util.withSuccess
import com.santaclose.lib.web.error.GraphqlErrorCode
import io.mockk.every
import io.mockk.justRun
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import javax.persistence.NoResultException

@SpringBootTest
@AutoConfigureWebTestClient
internal class MountainReviewAppMutationResolverTest @Autowired constructor(
    private val webTestClient: WebTestClient,
    @MockkBean
    private val mountainReviewAppMutationService: MountainReviewAppMutationService,
) {

    @Nested
    inner class CreateMountainReview {
        @Test
        fun `mountainId 가 유효하지 않으면 NOT FOUND 를 반환한다`() {
            // given
            val query = QueryInput(
                """mutation {
                |  createMountainReview(input: { 
                |    mountainId: "1", 
                |    title: "title"
                |    scenery: 5
                |    facility: 5
                |    traffic: 5
                |    content: "Good~"
                |  })
                |}
                """.trimMargin()
            )
            every { mountainReviewAppMutationService.register(any()) } throws NoResultException("no result")

            // when
            val response = webTestClient.query(query)

            // then
            response.withError(GraphqlErrorCode.NOT_FOUND, "no result")
        }

        @Test
        fun `정상적으로 생성한다`() {
            // given
            val query = QueryInput(
                """mutation {
                |  createMountainReview(input: { 
                |    mountainId: "1", 
                |    title: "title"
                |    scenery: 5
                |    facility: 5
                |    traffic: 5
                |    content: "Good~"
                |  })
                |}
                """.trimMargin()
            )
            justRun { mountainReviewAppMutationService.register(any()) }

            // when
            val response = webTestClient.query(query)

            // then
            response.withSuccess("createMountainReview")
        }
    }
}
