package com.santaclose.app.restaurantReview.repository

import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createQueryFactory
import com.santaclose.app.util.createRestaurant
import com.santaclose.app.util.createRestaurantReview
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class RestaurantReviewAppQueryRepositoryImplTest @Autowired constructor(
    private val em: EntityManager,
) {
    private val restaurantReviewAppQueryRepository =
        RestaurantReviewAppQueryRepositoryImpl(em.createQueryFactory())

    @Nested
    inner class FindAllByRestaurant {
        @Test
        fun `식당에 대한 최신 리뷰순으로 조회한다`() {
            // given
            val appUser = em.createAppUser()
            val restaurant = em.createRestaurant(appUser)
            val limit = 5
            repeat(limit) { em.createRestaurantReview(appUser, restaurant) }

            // when
            val result = restaurantReviewAppQueryRepository.findAllByRestaurant(restaurant.id, limit)

            // then
            result shouldHaveSize limit
            result.map { it.id } shouldBe result.map { it.id }.sortedDescending()
        }
    }

    @Nested
    inner class FindRestaurantRatingAverages {
        @Test
        fun `식당에 대한 평균 리뷰 및 리뷰 수를 조회한다`() {
            // given
            val appUser = em.createAppUser()
            val restaurant = em.createRestaurant(appUser)
            val restaurantReview = em.createRestaurantReview(appUser, restaurant)

            // when
            val result = restaurantReviewAppQueryRepository.findRestaurantRatingAverages(restaurant.id)

            // then
            assertSoftly(result) {
                val rating = restaurantReview.rating
                taste shouldBe rating.taste
                parkingSpace shouldBe rating.parkingSpace
                kind shouldBe rating.kind
                clean shouldBe rating.clean
                mood shouldBe rating.mood
                totalCount shouldBe 1
                average shouldBe 3.0
            }
        }
    }
}
