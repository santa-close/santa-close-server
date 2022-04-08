package com.santaclose.app.restaurantReview.service

import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurantReview.repository.RestaurantReviewAppRepository
import com.santaclose.app.restaurantReview.resolver.dto.CreateRestaurantReviewAppInput
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurantReview.RestaurantReview
import com.santaclose.lib.web.toLong
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import org.springframework.stereotype.Service

@Service
class RestaurantReviewAppMutationService(
  private val restaurantReviewAppRepository: RestaurantReviewAppRepository,
  private val restaurantRepository: RestaurantAppRepository,
  private val em: EntityManager
) {
  fun register(input: CreateRestaurantReviewAppInput, userId: Long) {
    val isRestaurantExist = restaurantRepository.existsById(input.restaurantId.toLong())
    if (!isRestaurantExist) {
      throw NoResultException("유효하지 않은 restaurantId 입니다.")
    }

    RestaurantReview(
        title = input.title,
        content = input.content,
        rating = input.rating.toEntity(),
        images = input.images.toMutableList(),
        restaurant = em.getReference(Restaurant::class.java, input.restaurantId.toLong()),
        appUser = em.getReference(AppUser::class.java, userId),
        priceAverage = input.priceAverage,
        priceComment = input.priceComment
      )
      .apply { restaurantReviewAppRepository.save(this) }
  }
}
