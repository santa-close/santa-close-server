package com.santaclose.app.location.service

import com.santaclose.app.location.repository.LocationAppRepository
import com.santaclose.app.location.resolver.dto.AppCoordinate
import com.santaclose.app.location.resolver.dto.LocationAppInput
import com.santaclose.app.location.resolver.dto.MountainAppLocation
import com.santaclose.app.location.resolver.dto.RestaurantAppLocation
import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createLocation
import com.santaclose.app.util.createMountain
import com.santaclose.app.util.createRestaurant
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.restaurant.type.FoodType
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.types.shouldBeInstanceOf
import javax.persistence.EntityManager
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
internal class LocationAppQueryServiceTest
@Autowired
constructor(
  locationAppRepository: LocationAppRepository,
  mountainAppRepository: MountainAppRepository,
  restaurantAppRepository: RestaurantAppRepository,
  private val em: EntityManager,
) {
  private val locationAppQueryService =
    LocationAppQueryService(locationAppRepository, mountainAppRepository, restaurantAppRepository)

  @Nested
  inner class Find {
    @Test
    fun `주어진 좌표 밖의 정보는 가져오지 않는다`() {
      // given
      val appUser = em.createAppUser()
      val location = em.createLocation(Location.create(longitude = 126.0, latitude = 37.0))
      em.createMountain(appUser, location)
      val input =
        LocationAppInput(
          diagonalFrom = AppCoordinate(longitude = 126.3, latitude = 37.6),
          diagonalTo = AppCoordinate(longitude = 126.5, latitude = 37.4),
        )

      // when
      val result = locationAppQueryService.find(input)

      // then
      result shouldHaveSize 0
    }

    @Test
    fun `주어진 좌표 안의 산 정보를 가져온다`() {
      // given
      val appUser = em.createAppUser()
      val location = em.createLocation(Location.create(longitude = 126.4, latitude = 37.5))
      em.createMountain(appUser, location)
      val input =
        LocationAppInput(
          diagonalFrom = AppCoordinate(longitude = 126.3, latitude = 37.6),
          diagonalTo = AppCoordinate(longitude = 126.5, latitude = 37.4),
        )

      // when
      val result = locationAppQueryService.find(input)

      // then
      result shouldHaveSize 1
      result.first().shouldBeInstanceOf<MountainAppLocation>()
    }

    @Test
    fun `주어진 좌표 안의 식당 정보를 가져온다`() {
      // given
      val appUser = em.createAppUser()
      val location = em.createLocation(Location.create(longitude = 126.4, latitude = 37.5))
      val foodTypes = listOf(FoodType.ASIA, FoodType.FOOD_COURT, FoodType.AMERICAN)
      em.createRestaurant(appUser, foodTypes)
      val input =
        LocationAppInput(
          diagonalFrom = AppCoordinate(longitude = 126.3, latitude = 37.6),
          diagonalTo = AppCoordinate(longitude = 126.5, latitude = 37.4),
        )

      // when
      val result = locationAppQueryService.find(input)

      // then
      result shouldHaveSize 1
      result.first().shouldBeInstanceOf<RestaurantAppLocation>()
    }
  }
}
