package com.santaclose.app.restaurant.repository

import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createLocation
import com.santaclose.app.util.createMountain
import com.santaclose.app.util.createMountainRestaurant
import com.santaclose.app.util.createQueryFactory
import com.santaclose.app.util.createRestaurant
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class RestaurantAppQueryRepositoryImplTest @Autowired constructor(
    private val em: EntityManager,
) {
    private val restaurantAppQueryRepository =
        RestaurantAppQueryRepositoryImpl(em.createQueryFactory())

    @Nested
    inner class FindLocationByMountain {
        @Test
        fun `주어진 산과 연결된 식당좌표를 가져온다`() {
            // given
            val appUser = em.createAppUser()
            val mountain = em.createMountain(appUser)
            val restaurant = em.createRestaurant(appUser)
            em.createMountainRestaurant(mountain, restaurant)

            // when
            val result = restaurantAppQueryRepository.findLocationByMountain(mountain.id)

            // then
            result.shouldBeRight().apply {
                this shouldHaveSize 1
                this[0].point.coordinates[0] shouldBe restaurant.location.point.coordinate
            }
        }
    }

    @Nested
    inner class FindOneWithLocation {
        @Test
        fun `식당과 함께 위치 정보를 조회한다`() {
            // given
            val appUser = em.createAppUser()
            val location = em.createLocation()
            val restaurant = em.createRestaurant(appUser, location)

            // when
            val result = restaurantAppQueryRepository.findOneWithLocation(restaurant.id)

            // then
            result.shouldBeRight().apply {
                id shouldBe restaurant.id
                location.id shouldBe restaurant.location.id
                location.point shouldBe restaurant.location.point
            }
        }
    }
}
