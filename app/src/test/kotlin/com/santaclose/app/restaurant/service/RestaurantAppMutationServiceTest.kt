package com.santaclose.app.restaurant.service

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurant.repository.RestaurantFoodTypeAppRepository
import com.santaclose.app.restaurant.resolver.dto.CreateRestaurantAppInput
import com.santaclose.app.util.createAppUser
import com.santaclose.lib.entity.restaurant.type.FoodType
import io.kotest.matchers.collections.shouldHaveSize
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
  private val mountainAppRepository: MountainAppRepository,
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
  inner class Register {
    val foodTypes = listOf(FoodType.ASIA, FoodType.FOOD_COURT, FoodType.AMERICAN)
    val images = listOf("image1", "image2", "image3", "image4")

    @Test
    fun `정상적으로 식당 정보를 저장한다 - 성공`() {
      // given
      val appUser = em.createAppUser()

      val input =
        CreateRestaurantAppInput(
          mountainId = ID("1"),
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
      restaurant.firstOrNull()?.let {
        //        restaurant.id.toString() shouldBe input.restaurantId.value
        //        appUser.id shouldBe appUser.id
        //        title shouldBe input.title
        //        content shouldBe input.content
        //        rating.mood shouldBe input.rating.mood.toByte()
        //        rating.kind shouldBe input.rating.kind.toByte()
        //        rating.parkingSpace shouldBe input.rating.parkingSpace.toByte()
        //        rating.clean shouldBe input.rating.clean.toByte()
        //        rating.taste shouldBe input.rating.taste.toByte()
        //        priceAverage shouldBe input.priceAverage
        //        priceComment shouldBe input.priceComment
        //        images shouldBe input.images
      }
    }
  }
}
