package com.santaclose.app.mountain.service

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.mountain.repository.MountainAppQueryRepositoryImpl
import com.santaclose.app.mountainReview.repository.MountainReviewAppQueryRepositoryImpl
import com.santaclose.app.restaurant.repository.RestaurantAppQueryRepositoryImpl
import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createMountain
import com.santaclose.app.util.createMountainRestaurant
import com.santaclose.app.util.createMountainReview
import com.santaclose.app.util.createQueryFactory
import com.santaclose.app.util.createRestaurant
import com.santaclose.lib.web.toID
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager
import javax.persistence.NoResultException

@DataJpaTest
internal class MountainAppQueryServiceTest
@Autowired
constructor(
    private val em: EntityManager,
) {
    private val mountainAppQueryService =
        MountainAppQueryService(
            MountainAppQueryRepositoryImpl(em.createQueryFactory()),
            MountainReviewAppQueryRepositoryImpl(em.createQueryFactory()),
            RestaurantAppQueryRepositoryImpl(em.createQueryFactory())
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
            val result = mountainAppQueryService.findDetail(mountain.id.toID())

            // then
            result.apply {
                name shouldBe mountain.name
                address shouldBe mountain.location.address
            }
        }

        @Test
        fun `mountainId가 유효하지 않으면 NoResultException 이 발생한다`() {
            // given
            val id = ID("-1")

            // when
            val exception = shouldThrow<NoResultException> { mountainAppQueryService.findDetail(id) }

            // then
            exception.message shouldBe "No entity found for query"
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
            result.mountain shouldBe mountain
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
            result.mountainRating.average shouldBe 20.0 / 6
            result.mountainRating.totalCount shouldBe 1
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
            result.restaurantLocations shouldHaveSize 1
            result.restaurantLocations.first().id shouldBe restaurant.id
        }
    }
}
