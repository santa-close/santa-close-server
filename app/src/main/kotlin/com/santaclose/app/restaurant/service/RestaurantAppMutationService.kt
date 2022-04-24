package com.santaclose.app.restaurant.service

import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurant.repository.RestaurantFoodTypeAppRepository
import com.santaclose.app.restaurant.resolver.dto.CreateRestaurantAppInput
import com.santaclose.lib.web.toLong
import javax.persistence.*
import org.springframework.stereotype.Service

@Service
class RestaurantAppMutationService(
  private val restaurantRepository: RestaurantAppRepository,
  private val restaurantFoodTypeAppRepository: RestaurantFoodTypeAppRepository
) {

  fun createRestaurant(input: CreateRestaurantAppInput, userId: Long) {
    val id = input.mountainId.toLong()
    val isExist = restaurantRepository.existsById(id)

    if (!isExist) {
      throw NoResultException("유효하지 않은 mountainId 입니다.")
    }

    val restaurantFoodTypes: List<RestaurantFoodType> =
      input.foodTypes.map {
        RestaurantFoodType(
          restaurant = null,
          foodType = it,
          appUser = em.getReference(AppUser::class.java, userId),
        )
      }

    val restaurant =
      Restaurant(
        name = input.name,
        description = input.description,
        images = input.images.toList(),
        restaurantFoodType = restaurantFoodTypes,
        appUser = em.getReference(AppUser::class.java, userId),
        location =
          Location.create(
            input.longitude.toDouble(),
            input.latitude.toDouble(),
            input.address,
            input.postcode
          ),
      )

    // TODO: JPARepository 대신 EntityManager persist 사용하는 이유?
    //    em.persist(restaurant)
    restaurantRepository.save(restaurant)
  }

  fun createRestaurantFoodType(input: CreateRestaurantAppInput, userId: Long) {}
}
