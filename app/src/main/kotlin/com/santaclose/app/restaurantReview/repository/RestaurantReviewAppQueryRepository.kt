package com.santaclose.app.restaurantReview.repository

import com.santaclose.app.restaurantReview.repository.dto.LatestRestaurantReviewDto
import com.santaclose.app.restaurantReview.repository.dto.RestaurantRatingAverageDto

interface RestaurantReviewAppQueryRepository {
  fun findAllByRestaurant(restaurantId: Long, limit: Int): List<LatestRestaurantReviewDto>

  fun findRestaurantRatingAverages(restaurantId: Long): RestaurantRatingAverageDto
}
