package com.santaclose.app.restaurantReview.controller

import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.restaurantReview.service.RestaurantReviewAppMutationService
import com.santaclose.app.util.AppContextMocker
import com.santaclose.app.util.GraphqlBody
import com.santaclose.app.util.gqlRequest
import com.santaclose.app.util.withError
import com.santaclose.app.util.withSuccess
import com.santaclose.lib.entity.appUser.type.AppUserRole
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
internal class RestaurantReviewAppMutationResolverTest @Autowired constructor(
    private val webTestClient: WebTestClient,
    @MockkBean private val restaurantReviewAppMutationService: RestaurantReviewAppMutationService
) : AppContextMocker() {
    @Nested
    inner class CreateRestaurantReview {
        @Test
        fun `에리가 발생하면 에러를 반환한다`() {
            // given
            val query = GraphqlBody(
                """mutation {
                |  createRestaurantReview(input: { 
                |    restaurantId: "1"
                |    title: "title"
                |    content: "content",
                |    rating: {
                |        taste: 5,
                |        parkingSpace: 5,
                |        kind: 5,
                |        clean: 5,
                |        mood: 5,
                |    }
                |    priceComment: IS_EXPENSIVE
                |    priceAverage: 10000
                |    images: [],
                |  })
                |}
                """.trimMargin()
            )
            every { restaurantReviewAppMutationService.register(any(), any()) } throws
                NoResultException("no result")
            withMockUser(AppUserRole.USER)

            // when
            val response = webTestClient.gqlRequest(query)

            // then
            response.withError(GraphqlErrorCode.NOT_FOUND, "no result")
        }

        @Test
        fun `정상적으로 생성된다`() {
            // given
            val query = GraphqlBody(
                """mutation {
                |  createRestaurantReview(input: { 
                |    restaurantId: "1"
                |    title: "title"
                |    content: "content",
                |    rating: {
                |        taste: 5,
                |        parkingSpace: 5,
                |        kind: 5,
                |        clean: 5,
                |        mood: 5,
                |    }
                |    priceComment: IS_EXPENSIVE
                |    priceAverage: 10000
                |    images: [],
                |  })
                |}
                """.trimMargin()
            )
            justRun { restaurantReviewAppMutationService.register(any(), any()) }
            withMockUser(AppUserRole.USER)

            // when
            val response = webTestClient.gqlRequest(query)

            // then
            response.withSuccess("createRestaurantReview")
        }
    }
}
