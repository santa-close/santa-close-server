package com.santaclose.app.restaurantReview.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID

data class RestaurantRatingAverage(
  val taste: Double,
  val parkingSpace: Double,
  val kind: Double,
  val clean: Double,
  val mood: Double,
  val totalCount: ID,
  val average: Double,
)
