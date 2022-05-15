package com.santaclose.app.restaurantReview.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.restaurantReview.repository.dto.RestaurantRatingAverageDto
import com.santaclose.lib.web.toID

data class RestaurantRatingAverage(
  val taste: Double,
  val parkingSpace: Double,
  val kind: Double,
  val clean: Double,
  val mood: Double,
  val totalCount: ID,
  val average: Double,
) {
  companion object {
    fun by(dto: RestaurantRatingAverageDto) =
      RestaurantRatingAverage(
        dto.taste,
        dto.parkingSpace,
        dto.kind,
        dto.clean,
        dto.mood,
        dto.totalCount.toID(),
        dto.average
      )
  }
}
