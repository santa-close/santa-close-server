package com.santaclose.app.restaurant.service

import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.restaurant.controller.dto.CreateRestaurantAppInput
import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createMountain
import com.santaclose.app.util.findAll
import com.santaclose.lib.entity.mountainRestaurant.MountainRestaurant
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurant.RestaurantFoodType
import com.santaclose.lib.entity.restaurant.type.FoodType
import io.kotest.assertions.assertSoftly
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class RestaurantAppMutationServiceTest @Autowired constructor(
    mountainAppRepository: MountainAppRepository,
    private val em: EntityManager,
) {
    private val restaurantAppMutationService = RestaurantAppMutationService(mountainAppRepository, em)

    @Nested
    inner class CreateRestaurant {
        @Test
        fun `정상적으로 식당 정보를 저장한다 - 성공`() {
            // given
            val appUser = em.createAppUser()
            val mountain = em.createMountain(appUser)
            val input =
                CreateRestaurantAppInput(
                    mountainIds = listOf(mountain.id.toString()),
                    name = "식당 이름",
                    images = listOf("image1", "image2", "image3", "image4"),
                    foodTypes = listOf(FoodType.ASIA, FoodType.FOOD_COURT, FoodType.AMERICAN),
                    longitude = 120.00,
                    latitude = 60.00,
                    address = "주소명",
                    postcode = "우편번호",
                )

            // when
            restaurantAppMutationService.createRestaurant(input, appUser.id)

            // then
            val restaurant = em.findAll<Restaurant>()
            restaurant shouldHaveSize 1
            assertSoftly(restaurant.first()) {
                name shouldBe input.name
                images shouldBe input.images
                location.point.x shouldBe input.longitude
                location.point.y shouldBe input.latitude
                location.address shouldBe input.address
                location.postcode shouldBe input.postcode
                appUser.id shouldBe appUser.id
            }
            em.findAll<RestaurantFoodType>().map { it.foodType } shouldBe input.foodTypes
            em.findAll<MountainRestaurant>().forAll {
                it.mountain shouldBe mountain
                it.restaurant shouldBe restaurant.first()
            }
        }
    }
}
