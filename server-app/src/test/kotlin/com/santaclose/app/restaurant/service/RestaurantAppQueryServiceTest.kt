package com.santaclose.app.restaurant.service

import com.santaclose.app.mountainRestaurant.repository.MountainRestaurantAppQueryRepositoryImpl
import com.santaclose.app.restaurant.repository.RestaurantAppQueryRepositoryImpl
import com.santaclose.app.restaurantReview.repository.RestaurantReviewAppQueryRepositoryImpl
import com.santaclose.app.restaurantReview.resolver.dto.RestaurantRatingAverage
import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createMountain
import com.santaclose.app.util.createMountainRestaurant
import com.santaclose.app.util.createQueryFactory
import com.santaclose.app.util.createRestaurant
import com.santaclose.app.util.createRestaurantReview
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager

@DataJpaTest
internal class RestaurantAppQueryServiceTest @Autowired constructor(private val em: EntityManager) {
    private val restaurantAppQueryRepository =
        RestaurantAppQueryRepositoryImpl(em.createQueryFactory())
    private val restaurantReviewAppQueryRepository =
        RestaurantReviewAppQueryRepositoryImpl(em.createQueryFactory())
    private val mountainRestaurantAppQueryRepository =
        MountainRestaurantAppQueryRepositoryImpl(em.createQueryFactory())

    private val restaurantAppQueryService =
        RestaurantAppQueryService(
            restaurantAppQueryRepository,
            restaurantReviewAppQueryRepository,
            mountainRestaurantAppQueryRepository,
        )

    @Nested
    inner class FindDetail {
        @Test
        fun `식당 상세 정보를 조회한다`() {
            // given
            val appUser = em.createAppUser()
            val restaurant = em.createRestaurant(appUser)
            val mountain = em.createMountain(appUser)
            val restaurantReview = em.createRestaurantReview(appUser, restaurant)
            em.createMountainRestaurant(mountain, restaurant)

            // when
            val result = restaurantAppQueryService.findDetail(restaurant.id)

            // then
            assertSoftly(result) {
                name shouldBe restaurant.name
                address shouldBe restaurant.location.address
                foodType shouldBe restaurant.restaurantFoodType.map { it.foodType }
                restaurantRatingAverage.shouldBeInstanceOf<RestaurantRatingAverage>()
                restaurantRatingAverage.kind shouldBe restaurantReview.rating.kind
                restaurantReviews shouldHaveSize 1
                mountains shouldHaveSize 1
            }
        }
    }
}
