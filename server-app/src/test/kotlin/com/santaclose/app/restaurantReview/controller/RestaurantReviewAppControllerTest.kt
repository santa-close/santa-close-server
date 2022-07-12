package com.santaclose.app.restaurantReview.controller

import arrow.core.some
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.auth.security.AppSession
import com.santaclose.app.auth.security.ServerRequestParser
import com.santaclose.app.restaurantReview.controller.dto.CreateRestaurantReviewAppInput
import com.santaclose.app.restaurantReview.controller.dto.RestaurantRatingInput
import com.santaclose.app.restaurantReview.service.RestaurantReviewAppMutationService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.entity.restaurantReview.type.PriceComment
import io.kotest.core.spec.style.FreeSpec
import io.mockk.every
import io.mockk.justRun
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.HttpGraphQlTester
import javax.persistence.NoResultException

@SpringBootTest
@AutoConfigureHttpGraphQlTester
internal class RestaurantReviewAppControllerTest(
    private val graphQlTester: HttpGraphQlTester,
    @MockkBean
    private val restaurantReviewAppMutationService: RestaurantReviewAppMutationService,
    @MockkBean
    private val serverRequestParser: ServerRequestParser,
) : FreeSpec(
    {

        "createRestaurantReview" - {
            "등록에 실패하면 에러를 반환한다" {
                // given
                val input = CreateRestaurantReviewAppInput(
                    restaurantId = "1",
                    title = "title",
                    content = "content",
                    rating = RestaurantRatingInput(
                        taste = 5,
                        parkingSpace = 5,
                        kind = 5,
                        clean = 5,
                        mood = 5,
                    ),
                    priceComment = PriceComment.IS_EXPENSIVE,
                    priceAverage = 10000,
                    images = emptyList(),
                )
                val session = AppSession(123, AppUserRole.USER)

                every { serverRequestParser.parse(any()) } returns session.some()
                every { restaurantReviewAppMutationService.register(any(), any()) } throws
                    NoResultException("no result")

                // when
                val response = graphQlTester
                    .documentName("createRestaurantReview")
                    .variable("input", input)
                    .execute()

                // then
                response
                    .errors()
                    .expect { it.message == "no result" }
            }

            "정상적으로 생성된다" {
                val input = CreateRestaurantReviewAppInput(
                    restaurantId = "1",
                    title = "title",
                    content = "content",
                    rating = RestaurantRatingInput(
                        taste = 5,
                        parkingSpace = 5,
                        kind = 5,
                        clean = 5,
                        mood = 5,
                    ),
                    priceComment = PriceComment.IS_EXPENSIVE,
                    priceAverage = 10000,
                    images = emptyList(),
                )
                val session = AppSession(123, AppUserRole.USER)

                every { serverRequestParser.parse(any()) } returns session.some()
                justRun { restaurantReviewAppMutationService.register(any(), any()) }

                // when
                val response = graphQlTester
                    .documentName("createRestaurantReview")
                    .variable("input", input)
                    .execute()

                // then
                response
                    .path("createRestaurantReview")
                    .entity(Boolean::class.java)
                    .isEqualTo(true)
            }
        }
    },
)
