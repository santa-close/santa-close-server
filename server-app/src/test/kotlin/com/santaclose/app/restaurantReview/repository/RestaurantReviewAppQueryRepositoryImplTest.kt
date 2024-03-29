package com.santaclose.app.restaurantReview.repository

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createRestaurant
import com.santaclose.app.util.createRestaurantReview
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.collections.shouldBeSortedWith
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
        RestaurantReviewAppQueryRepositoryImpl(em, JpqlRenderContext())

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
            result.shouldBeRight().apply {
                this shouldHaveSize limit
                shouldBeSortedWith(compareByDescending { it.id })
            } shouldHaveSize limit
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
            result.shouldBeRight().apply {
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
