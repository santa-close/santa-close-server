package com.santaclose.app.restaurantReview.resolver.dto

import com.santaclose.lib.entity.restaurantReview.RestaurantRating
import org.hibernate.validator.constraints.Range

data class RestaurantRatingDto(
  @field:Range(min = 1, max = 5) val taste: Int,
  @field:Range(min = 1, max = 5) val parkingSpace: Int,
  @field:Range(min = 1, max = 5) val kind: Int,
  @field:Range(min = 1, max = 5) val clean: Int,
  @field:Range(min = 1, max = 5) val mood: Int,
) {
  fun toEntity() =
    this.let {
      RestaurantRating(
        it.taste.toByte(),
        it.parkingSpace.toByte(),
        it.kind.toByte(),
        it.clean.toByte(),
        it.mood.toByte()
      )
    }
}
