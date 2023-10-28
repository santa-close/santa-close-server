package com.santaclose.app.restaurant.service

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.santaclose.app.mountain.repository.MountainAppQueryRepositoryImpl
import com.santaclose.app.mountainRestaurant.repository.MountainRestaurantAppQueryRepositoryImpl
import com.santaclose.app.restaurant.repository.RestaurantAppQueryRepositoryImpl
import com.santaclose.app.restaurantReview.controller.dto.RestaurantRatingAverage
import com.santaclose.app.restaurantReview.repository.RestaurantReviewAppQueryRepositoryImpl
import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createMountain
import com.santaclose.app.util.createMountainRestaurant
import com.santaclose.app.util.createRestaurant
import com.santaclose.app.util.createRestaurantReview
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class RestaurantAppQueryServiceTest @Autowired constructor(
    private val em: EntityManager,
) {
    private val jpqlRenderContext = JpqlRenderContext()
    private val restaurantAppQueryRepository =
        RestaurantAppQueryRepositoryImpl(em, jpqlRenderContext)
    private val restaurantReviewAppQueryRepository =
        RestaurantReviewAppQueryRepositoryImpl(em, jpqlRenderContext)
    private val mountainRestaurantAppQueryRepository =
        MountainRestaurantAppQueryRepositoryImpl(em, jpqlRenderContext)
    private val mountainAppQueryRepository =
        MountainAppQueryRepositoryImpl(em, jpqlRenderContext)

    private val restaurantAppQueryService =
        RestaurantAppQueryService(
            restaurantAppQueryRepository,
            restaurantReviewAppQueryRepository,
            mountainRestaurantAppQueryRepository,
            mountainAppQueryRepository,
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
            result.shouldBeRight().apply {
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

    @Nested
    inner class FindOneSummary {
        @Test
        fun `식당 기본정보를 가져온다`() {
            // given
            val appUser = em.createAppUser()
            val restaurant = em.createRestaurant(appUser)

            // when
            val result = restaurantAppQueryService.findOneSummary(restaurant.id)

            // then
            result.shouldBeRight().restaurant shouldBe restaurant
        }

        @Test
        fun `식당 후기의 평균과 개수정보를 가져온다`() {
            // given
            val appUser = em.createAppUser()
            val restaurant = em.createRestaurant(appUser)
            em.createRestaurantReview(appUser, restaurant)

            // when
            val result = restaurantAppQueryService.findOneSummary(restaurant.id)

            // then
            result.shouldBeRight().apply {
                restaurantRating.average shouldBe 3.0
                restaurantRating.totalCount shouldBe 1
            }
        }

        @Test
        fun `식당과 연결된 산의 위치정보를 가져온다`() {
            // given
            val appUser = em.createAppUser()
            val restaurant = em.createRestaurant(appUser)
            val mountain = em.createMountain(appUser)
            em.createMountainRestaurant(mountain, restaurant)

            // when
            val result = restaurantAppQueryService.findOneSummary(restaurant.id)

            // then
            result.shouldBeRight().apply {
                mountainLocations shouldHaveSize 1
                mountainLocations.first().id shouldBe mountain.id
            }
        }
    }
}
