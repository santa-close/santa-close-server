package com.santaclose.app.restaurant.service

import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.restaurant.controller.dto.CreateRestaurantAppInput
import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurant.repository.RestaurantFoodTypeAppRepository
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurant.RestaurantFoodType
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.transaction.Transactional

@Service
class RestaurantAppMutationService(
    private val restaurantRepository: RestaurantAppRepository,
    private val mountainAppRepository: MountainAppRepository,
    private val restaurantFoodTypeAppRepository: RestaurantFoodTypeAppRepository,
    private val em: EntityManager,
) {

    @Transactional
    fun createRestaurant(input: CreateRestaurantAppInput, userId: Long) {
        val id = input.mountainId.toLong()
        val isExistMountain = mountainAppRepository.existsById(id)
        if (!isExistMountain) {
            throw NoResultException("유효하지 않은 mountainId 입니다.")
        }

        val restaurant =
            Restaurant(
                name = input.name,
                description = input.description,
                images = input.images,
                appUser = em.getReference(AppUser::class.java, userId),
                location = Location.create(input.longitude, input.latitude, input.address, input.postcode),
            )
        val restaurantFoodTypes =
            input.foodTypes.map { RestaurantFoodType(restaurant = restaurant, foodType = it) }

        restaurantRepository.save(restaurant)
        restaurantFoodTypeAppRepository.saveAll(restaurantFoodTypes)
    }
}
