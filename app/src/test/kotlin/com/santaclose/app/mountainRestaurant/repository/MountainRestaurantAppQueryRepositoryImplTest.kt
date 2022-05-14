package com.santaclose.app.mountainRestaurant.repository

import com.santaclose.app.util.*
import com.santaclose.lib.entity.restaurant.type.FoodType
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import javax.persistence.EntityManager
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class MountainRestaurantAppQueryRepositoryImplTest
@Autowired
constructor(
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
      val foodTypes = listOf(FoodType.ASIA)
      val restaurant = em.createRestaurant(appUser, foodTypes)
      repeat(10) {
        val mountain = em.createMountain(appUser)
        em.createMountainRestaurant(mountain, restaurant)
      }
      val limit = 5

      // when
      val result =
        mountainRestaurantAppQueryRepository.findMountainByRestaurant(restaurant.id, limit)

      // then
      result shouldHaveSize limit
      result.map { it.id } shouldBe result.map { it.id }.sortedDescending()
    }
  }
}
