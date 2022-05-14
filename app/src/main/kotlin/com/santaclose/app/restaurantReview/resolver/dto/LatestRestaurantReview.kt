package com.santaclose.app.restaurantReview.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID

data class LatestRestaurantReview(
  val id: ID,
  val title: String,
  val content: String,
)
