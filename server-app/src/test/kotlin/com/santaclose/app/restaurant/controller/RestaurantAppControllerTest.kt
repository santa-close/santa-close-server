package com.santaclose.app.restaurant.controller

import arrow.core.some
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.auth.security.AppSession
import com.santaclose.app.auth.security.ServerRequestParser
import com.santaclose.app.restaurant.controller.dto.CreateRestaurantAppInput
import com.santaclose.app.restaurant.controller.dto.RestaurantAppDetail
import com.santaclose.app.restaurant.service.RestaurantAppMutationService
import com.santaclose.app.restaurant.service.RestaurantAppQueryService
import com.santaclose.app.restaurantReview.controller.dto.RestaurantRatingAverage
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.entity.restaurant.type.FoodType
import io.kotest.core.spec.style.FreeSpec
import io.mockk.every
import io.mockk.justRun
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.HttpGraphQlTester

@SpringBootTest
@AutoConfigureHttpGraphQlTester
internal class RestaurantAppControllerTest @Autowired constructor(
    private val graphQlTester: HttpGraphQlTester,
    @MockkBean
    private val restaurantAppQueryService: RestaurantAppQueryService,
    @MockkBean
    private val restaurantAppMutationService: RestaurantAppMutationService,
    @MockkBean
    private val serverRequestParser: ServerRequestParser,
) : FreeSpec(
    {

        "restaurantDetail" - {
            "유저가 식당을 조회한다" {
                // given
                val detail = RestaurantAppDetail(
                    name = "name",
                    address = "address",
                    foodType = listOf(FoodType.FOOD_COURT),
                    restaurantRatingAverage = RestaurantRatingAverage(1.0, 2.0, 3.0, 4.0, 5.0, 6, 7.0),
                    restaurantReviews = listOf(),
                    mountains = listOf(),
                )
                val session = AppSession(123, AppUserRole.USER)

                every { serverRequestParser.parse(any()) } returns session.some()
                every { restaurantAppQueryService.findDetail(any()) } returns detail

                // when
                val response = graphQlTester
                    .documentName("restaurantDetail")
                    .variable("id", "1")
                    .execute()

                // then
                response
                    .path("restaurantDetail")
                    .entity(RestaurantAppDetail::class.java)
                    .isEqualTo(detail)
            }
        }

        "createRestaurant" - {
            "정상적으로 사용자가 식당을 등록한다 - 성공" {
                // given
                val input = CreateRestaurantAppInput(
                    mountainId = "1",
                    name = "식당 이름",
                    description = "식당 설명",
                    images = listOf("image1", "image2", "image3"),
                    foodTypes = listOf(FoodType.ASIA),
                    longitude = 100.00,
                    latitude = 60.00,
                    address = "주소명",
                    postcode = "우편번호",
                )
                val session = AppSession(123, AppUserRole.USER)

                every { serverRequestParser.parse(any()) } returns session.some()
                justRun { restaurantAppMutationService.createRestaurant(any(), any()) }

                // when
                val response = graphQlTester
                    .documentName("createRestaurant")
                    .variable("input", input)
                    .execute()

                // then
                response
                    .path("createRestaurant")
                    .entity(Boolean::class.java)
                    .isEqualTo(true)
            }
        }
    },
)
