package com.santaclose.app.restaurantReview.repository.dto

import com.santaclose.app.restaurantReview.resolver.dto.RestaurantRatingAverage
import com.santaclose.lib.web.toID

data class RestaurantRatingAverageDto(
  val taste: Double,
  val parkingSpace: Double,
  val kind: Double,
  val clean: Double,
  val mood: Double,
  val totalCount: Long,
) {
  val average = (taste + parkingSpace + kind + clean + mood) / 5

  fun toDetail() =
    RestaurantRatingAverage(taste, parkingSpace, kind, clean, mood, totalCount.toID(), average)
}
