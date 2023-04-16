package com.santaclose.app.restaurantReview.service

import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurantReview.controller.dto.CreateRestaurantReviewAppInput
import com.santaclose.app.restaurantReview.controller.dto.RestaurantRatingInput
import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createRestaurant
import com.santaclose.app.util.findAll
import com.santaclose.lib.entity.restaurantReview.RestaurantReview
import com.santaclose.lib.entity.restaurantReview.type.PriceComment
import com.santaclose.lib.web.exception.DomainError
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class RestaurantReviewAppMutationServiceTest @Autowired constructor(
    restaurantRepository: RestaurantAppRepository,
    private val em: EntityManager,
) {
    private val restaurantReviewAppMutationService =
        RestaurantReviewAppMutationService(restaurantRepository, em)

    @Nested
    inner class Register {
        @Test
        fun `restaurantId가 유효하지 않으면 NotFound에러를 반환한다`() {
            // given
            val appUser = em.createAppUser()
            val input =
                CreateRestaurantReviewAppInput(
                    restaurantId = "-1",
                    "title",
                    "content",
                    RestaurantRatingInput(1, 2, 3, 4, 5),
                    PriceComment.IS_CHEAP,
                    10000,
                    mutableListOf(),
                )

            // when
            val result = restaurantReviewAppMutationService.register(input, appUser.id)

            // then
            result shouldBeLeft DomainError.NotFound("유효하지 않은 restaurantId 입니다: ${input.restaurantId}")
        }

        @Test
        fun `RestaurantReview를 저장한다`() {
            // given
            val appUser = em.createAppUser()
            val restaurant = em.createRestaurant(appUser)
            val input =
                CreateRestaurantReviewAppInput(
                    restaurant.id.toString(),
                    "title",
                    "content",
                    RestaurantRatingInput(1, 2, 3, 4, 5),
                    PriceComment.IS_CHEAP,
                    10000,
                    mutableListOf("url"),
                )

            // when
            restaurantReviewAppMutationService.register(input, appUser.id)

            // then
            val restaurantReview = em.findAll<RestaurantReview>()
            restaurantReview shouldHaveSize 1
            restaurantReview[0].apply {
                restaurant.id.toString() shouldBe input.restaurantId
                appUser.id shouldBe appUser.id
                title shouldBe input.title
                content shouldBe input.content
                rating.mood shouldBe input.rating.mood.toByte()
                rating.kind shouldBe input.rating.kind.toByte()
                rating.parkingSpace shouldBe input.rating.parkingSpace.toByte()
                rating.clean shouldBe input.rating.clean.toByte()
                rating.taste shouldBe input.rating.taste.toByte()
                priceAverage shouldBe input.priceAverage
                priceComment shouldBe input.priceComment
                images shouldBe input.images
            }
        }
    }
}
