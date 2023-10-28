package com.santaclose.app.mountain.service

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.santaclose.app.mountain.repository.MountainAppQueryRepositoryImpl
import com.santaclose.app.mountainRestaurant.repository.MountainRestaurantAppQueryRepositoryImpl
import com.santaclose.app.mountainReview.repository.MountainReviewAppQueryRepositoryImpl
import com.santaclose.app.restaurant.repository.RestaurantAppQueryRepositoryImpl
import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createMountain
import com.santaclose.app.util.createMountainRestaurant
import com.santaclose.app.util.createMountainReview
import com.santaclose.app.util.createQueryFactory
import com.santaclose.app.util.createRestaurant
import com.santaclose.lib.web.exception.DomainError.NotFound
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class MountainAppQueryServiceTest @Autowired constructor(
    private val em: EntityManager,
) {
    private val mountainAppQueryService =
        MountainAppQueryService(
            MountainAppQueryRepositoryImpl(em.createQueryFactory()),
            MountainReviewAppQueryRepositoryImpl(em.createQueryFactory()),
            RestaurantAppQueryRepositoryImpl(em, JpqlRenderContext()),
            MountainRestaurantAppQueryRepositoryImpl(em.createQueryFactory()),
        )

    @Nested
    inner class FindDetail {
        @Test
        fun `산 상세 정보를 조회하여 반환한다`() {
            // given
            val user = em.createAppUser()
            val mountain = em.createMountain(user)
            em.createMountainReview(user, mountain)

            // when
            val result = mountainAppQueryService.findDetail(mountain.id.toString())

            // then
            result.shouldBeRight().apply {
                name shouldBe mountain.name
                address shouldBe mountain.location.address
            }
        }

        @Test
        fun `mountainId가 유효하지 않으면 NotFound 에러가 발생한다 `() {
            // given
            val id = "-1"

            // when/then
            mountainAppQueryService.findDetail(id) shouldBeLeft NotFound("산이 존재하지 않습니다: $id")
        }
    }

    @Nested
    inner class FindOneSummary {
        @Test
        fun `산 기본정보를 가져온다`() {
            // given
            val appUser = em.createAppUser()
            val mountain = em.createMountain(appUser)

            // when
            val result = mountainAppQueryService.findOneSummary(mountain.id)

            // then
            result.shouldBeRight().mountain shouldBe mountain
        }

        @Test
        fun `산 후기의 평균과 개수정보를 가져온다`() {
            // given
            val appUser = em.createAppUser()
            val mountain = em.createMountain(appUser)
            em.createMountainReview(appUser, mountain)

            // when
            val result = mountainAppQueryService.findOneSummary(mountain.id)

            // then
            result.shouldBeRight().apply {
                mountainRating.average shouldBe 20.0 / 6
                mountainRating.totalCount shouldBe 1
            }
        }

        @Test
        fun `산과 연결된 식당의 위치정보를 가져온다`() {
            // given
            val appUser = em.createAppUser()
            val mountain = em.createMountain(appUser)
            val restaurant = em.createRestaurant(appUser)
            em.createMountainRestaurant(mountain, restaurant)

            // when
            val result = mountainAppQueryService.findOneSummary(mountain.id)

            // then
            result.shouldBeRight().apply {
                restaurantLocations shouldHaveSize 1
                restaurantLocations.first().id shouldBe restaurant.id
            }
        }
    }
}
