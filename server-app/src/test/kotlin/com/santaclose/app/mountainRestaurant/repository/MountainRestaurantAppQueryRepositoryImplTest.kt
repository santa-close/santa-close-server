package com.santaclose.app.mountainRestaurant.repository

import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createMountain
import com.santaclose.app.util.createMountainRestaurant
import com.santaclose.app.util.createQueryFactory
import com.santaclose.app.util.createRestaurant
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager

@DataJpaTest
internal class MountainRestaurantAppQueryRepositoryImplTest @Autowired constructor(
    private val em: EntityManager,
) {
    private val mountainRestaurantAppQueryRepository =
        MountainRestaurantAppQueryRepositoryImpl(em.createQueryFactory())

    @Nested
    inner class FindMountainByRestaurant {
        @Test
        fun `음식점에 연관된 산 리스트를 최신순으로 조회한다`() {
            // given
            val appUser = em.createAppUser()
            val restaurant = em.createRestaurant(appUser)
            repeat(10) {
                val mountain = em.createMountain(appUser)
                em.createMountainRestaurant(mountain, restaurant)
            }
            val limit = 5

            // when
            val result = mountainRestaurantAppQueryRepository.findMountainByRestaurant(restaurant.id, limit)

            // then
            result shouldHaveSize limit
            result.map { it.id } shouldBe result.map { it.id }.sortedDescending()
        }
    }

    @Nested
    inner class FindRestaurantByMountain {
        @Test
        fun `산에 연관된 식당 리스트를 최신순으로 조회한다`() {
            // given
            val appUser = em.createAppUser()
            val mountain = em.createMountain(appUser)
            repeat(10) {
                val restaurant = em.createRestaurant(appUser)
                em.createMountainRestaurant(mountain, restaurant)
            }
            val limit = 5

            // when
            val result = mountainRestaurantAppQueryRepository.findRestaurantByMountain(mountain.id, limit)

            // then
            result shouldHaveSize limit
            result.map { it.id } shouldBe result.map { it.id }.sortedDescending()
        }
    }
}
