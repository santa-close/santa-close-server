package com.santaclose.app.restaurantReview.resolver.dto

import com.santaclose.lib.entity.restaurantReview.RestaurantRating
import org.hibernate.validator.constraints.Range

data class RestaurantRatingInput(
  @field:Range(min = 1, max = 5) val taste: Int,
  @field:Range(min = 1, max = 5) val parkingSpace: Int,
  @field:Range(min = 1, max = 5) val kind: Int,
  @field:Range(min = 1, max = 5) val clean: Int,
  @field:Range(min = 1, max = 5) val mood: Int,
) {
  fun toEntity() =
    RestaurantRating(
      taste.toByte(),
      parkingSpace.toByte(),
      kind.toByte(),
      clean.toByte(),
      mood.toByte()
    )
}
