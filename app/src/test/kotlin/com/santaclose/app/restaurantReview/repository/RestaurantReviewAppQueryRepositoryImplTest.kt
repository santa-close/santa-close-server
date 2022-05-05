package com.santaclose.app.restaurantReview.repository

import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createQueryFactory
import com.santaclose.app.util.createRestaurant
import com.santaclose.app.util.createRestaurantReview
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import javax.persistence.EntityManager
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class RestaurantReviewAppQueryRepositoryImplTest
@Autowired
constructor(private val em: EntityManager) {
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
      result.also {
        val rating = restaurantReview.rating
        it.taste shouldBe rating.taste
        it.parkingSpace shouldBe rating.parkingSpace
        it.kind shouldBe rating.kind
        it.clean shouldBe rating.clean
        it.mood shouldBe rating.mood
        it.totalCount shouldBe 1
        it.average shouldBe 3.0
      }
    }
  }
}
