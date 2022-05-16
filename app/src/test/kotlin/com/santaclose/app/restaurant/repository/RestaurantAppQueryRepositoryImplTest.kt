package com.santaclose.app.restaurant.repository

import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createLocation
import com.santaclose.app.util.createQueryFactory
import com.santaclose.app.util.createRestaurant
import io.kotest.matchers.shouldBe
import javax.persistence.EntityManager
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class RestaurantAppQueryRepositoryImplTest
@Autowired
constructor(private val em: EntityManager) {
  private val restaurantAppQueryRepository =
    RestaurantAppQueryRepositoryImpl(em.createQueryFactory())

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
      result.apply {
        id shouldBe restaurant.id
        location.id shouldBe restaurant.location.id
        location.point shouldBe restaurant.location.point
      }
    }
  }
}
