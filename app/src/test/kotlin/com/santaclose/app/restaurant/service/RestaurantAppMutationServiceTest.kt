package com.santaclose.app.restaurant.service

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurant.repository.RestaurantFoodTypeAppRepository
import com.santaclose.app.restaurant.resolver.dto.CreateRestaurantAppInput
import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createMountain
import com.santaclose.lib.entity.restaurant.type.FoodType
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import javax.persistence.EntityManager
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class RestaurantAppMutationServiceTest
@Autowired
constructor(
  private val restaurantRepository: RestaurantAppRepository,
  mountainAppRepository: MountainAppRepository,
  private val restaurantFoodTypeAppRepository: RestaurantFoodTypeAppRepository,
  private val em: EntityManager
) {

  private val restaurantAppMutationService =
    RestaurantAppMutationService(
      restaurantRepository,
      mountainAppRepository,
      restaurantFoodTypeAppRepository,
      em
    )

  @Nested
  inner class CreateRestaurant {
    private val foodTypes = listOf(FoodType.ASIA, FoodType.FOOD_COURT, FoodType.AMERICAN)
    private val images = listOf("image1", "image2", "image3", "image4")

    @Test
    fun `정상적으로 식당 정보를 저장한다 - 성공`() {
      // given
      val appUser = em.createAppUser()
      val mountain = em.createMountain(appUser)
      val input =
        CreateRestaurantAppInput(
          mountainId = ID(mountain.id.toString()),
          name = "식당 이름",
          description = "식당 설명",
          images = images,
          foodTypes = foodTypes,
          longitude = 120.00,
          latitude = 60.00,
          address = "주소명",
          postcode = "우편번호"
        )

      // when
      restaurantAppMutationService.createRestaurant(input, appUser.id)

      // then
      val restaurant = restaurantRepository.findAll()
      restaurant shouldHaveSize 1
      assertSoftly(restaurant.first()) {
        name shouldBe input.name
        description shouldBe input.description
        images shouldBe input.images
        location.point.x shouldBe input.longitude
        location.point.y shouldBe input.latitude
        location.address shouldBe input.address
        location.postcode shouldBe input.postcode
        appUser.id shouldBe appUser.id
      }
      val foodTypes = restaurantFoodTypeAppRepository.findAll()
      foodTypes.map { it.foodType } shouldBe input.foodTypes
    }
  }
}
