package com.santaclose.app.restaurantReview.service

import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurantReview.repository.RestaurantReviewAppRepository
import com.santaclose.app.restaurantReview.resolver.dto.CreateRestaurantReviewInput
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurantReview.RestaurantReview
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import org.springframework.stereotype.Service

@Service
class RestaurantReviewAppMutationService(
  private val mountainAppRepository: MountainAppRepository,
  private val restaurantReviewAppRepository: RestaurantReviewAppRepository,
  private val restaurantRepository: RestaurantAppRepository,
  private val em: EntityManager
) {
  fun register(input: CreateRestaurantReviewInput, userId: Long) {
    val isMountainExist = mountainAppRepository.existsById(input.mountainId.toLong())
    if (!isMountainExist) {
      throw NoResultException("유효하지 않은 mountainId 입니다.")
    }

    val isRestaurantExist = restaurantRepository.existsById(input.restaurantId.toLong())
    if (!isRestaurantExist) {
      throw NoResultException("유효하지 않은 restaurantId 입니다.")
    }

    RestaurantReview(
        title = input.title,
        content = input.content,
        rating = input.rating,
        images = input.images.toMutableList(),
        restaurant = em.getReference(Restaurant::class.java, input.restaurantId.toLong()),
        mountain = em.getReference(Mountain::class.java, input.mountainId.toLong()),
        appUser = em.getReference(AppUser::class.java, userId)
      )
      .apply { restaurantReviewAppRepository.save(this) }
  }
}
