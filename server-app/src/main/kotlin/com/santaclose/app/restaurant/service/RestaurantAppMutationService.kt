package com.santaclose.app.restaurant.service

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.mountain.repository.has
import com.santaclose.app.restaurant.controller.dto.CreateRestaurantAppInput
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainRestaurant.MountainRestaurant
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurant.RestaurantFoodType
import com.santaclose.lib.web.exception.DomainError
import com.santaclose.lib.web.exception.catchDB
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class RestaurantAppMutationService(
    private val mountainAppRepository: MountainAppRepository,
    private val em: EntityManager,
) {

    @Transactional
    fun createRestaurant(input: CreateRestaurantAppInput, userId: Long): Either<DomainError, Unit> = either {
        input.mountainIds.forEach {
            val id = it.toLong()
            val found = mountainAppRepository.has(id).bind()

            ensure(found) { DomainError.NotFound("유효하지 않은 mountainId 입니다: $id") }
        }

        val restaurant =
            Restaurant(
                name = input.name,
                description = "",
                images = input.images,
                appUser = em.getReference(AppUser::class.java, userId),
                location = Location.create(input.longitude, input.latitude, input.address, input.postcode),
            )
        val restaurantFoodTypes =
            input.foodTypes.map { RestaurantFoodType(restaurant = restaurant, foodType = it) }
        val mountainRestaurants = input.mountainIds.map {
            MountainRestaurant(
                restaurant = restaurant,
                mountain = em.getReference(Mountain::class.java, it.toLong()),
            )
        }

        Either.catchDB {
            em.persist(restaurant)
            restaurantFoodTypes.forEach(em::persist)
            mountainRestaurants.forEach(em::persist)
        }.bind()
    }
}
