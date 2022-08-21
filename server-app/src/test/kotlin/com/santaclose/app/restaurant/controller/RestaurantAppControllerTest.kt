package com.santaclose.app.restaurant.controller

import arrow.core.some
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.auth.security.AppSession
import com.santaclose.app.auth.security.ServerRequestParser
import com.santaclose.app.restaurant.controller.dto.CreateRestaurantAppInput
import com.santaclose.app.restaurant.controller.dto.RestaurantAppDetail
import com.santaclose.app.restaurant.controller.dto.RestaurantAppSummary
import com.santaclose.app.restaurant.service.RestaurantAppMutationService
import com.santaclose.app.restaurant.service.RestaurantAppQueryService
import com.santaclose.app.restaurant.service.dto.RestaurantSummaryDto
import com.santaclose.app.restaurantReview.controller.dto.RestaurantRatingAverage
import com.santaclose.app.restaurantReview.repository.dto.RestaurantRatingAverageDto
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurant.type.FoodType
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
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
                    mountainIds = listOf("1"),
                    name = "식당 이름",
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

        "restaurantSummary" - {
            "식당 요약정보를 가져온다" {
                // given
                val dto =
                    RestaurantSummaryDto(
                        restaurant = Restaurant(
                            name = "test mountain",
                            images = mutableListOf("test.jpg"),
                            description = "description",
                            appUser = AppUser("name", "email", "socialId", AppUserRole.USER),
                            location =
                            Location.create(
                                longitude = 10.0,
                                latitude = 10.0,
                                address = "test address",
                                postcode = "123-1234",
                            ),
                        ).also { it.id = 100 },
                        mountainLocations = emptyList(),
                        restaurantRating = RestaurantRatingAverageDto.empty,
                    )
                val session = AppSession(123, AppUserRole.USER)

                every { serverRequestParser.parse(any()) } returns session.some()
                coEvery { restaurantAppQueryService.findOneSummary(123) } returns dto

                // when
                val response = graphQlTester
                    .documentName("restaurantSummary")
                    .variable("id", session.id)
                    .execute()

                // then
                response
                    .path("restaurantSummary")
                    .entity(RestaurantAppSummary::class.java)
                    .satisfies {
                        it.id shouldBe "${dto.restaurant.id}"
                        it.address shouldBe "test address"
                        it.rating shouldBe 0.0
                        it.reviewCount shouldBe 0
                    }
            }
        }
    },
)
