package com.santaclose.app.restaurant.service

import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurant.repository.RestaurantFoodTypeAppRepository
import com.santaclose.app.restaurant.resolver.dto.CreateRestaurantAppInput
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurant.RestaurantFoodType
import com.santaclose.lib.web.toLong
import javax.persistence.*
import org.springframework.stereotype.Service

@Service
class RestaurantAppMutationService(
  private val restaurantRepository: RestaurantAppRepository,
  private val mountainAppRepository: MountainAppRepository,
  private val restaurantFoodTypeAppRepository: RestaurantFoodTypeAppRepository,
  private val em: EntityManager
) {

  fun createRestaurant(input: CreateRestaurantAppInput, userId: Long) {
    val id = input.mountainId.toLong()
    val isExistMountain = mountainAppRepository.existsById(id)
    if (!isExistMountain) {
      throw NoResultException("유효하지 않은 mountainId 입니다.")
    }

    val restaurantFoodTypes: MutableList<RestaurantFoodType> =
      input
        .foodTypes
        .map {
          RestaurantFoodType(
            restaurant = null,
            foodType = it,
          )
        }
        .toMutableList()

    val restaurant =
      Restaurant(
        name = input.name,
        description = input.description,
        images = input.images,
        restaurantFoodType = restaurantFoodTypes,
        appUser = em.getReference(AppUser::class.java, userId),
        location = Location.create(input.longitude, input.latitude, input.address, input.postcode),
      )

    // TODO: JPARepository 대신 EntityManager persist 사용하는 이유?
    //    em.persist(restaurant)
    restaurantRepository.save(restaurant)
  }
}
