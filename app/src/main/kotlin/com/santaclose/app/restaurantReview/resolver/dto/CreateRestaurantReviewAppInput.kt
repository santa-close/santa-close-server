package com.santaclose.app.restaurantReview.resolver.dto

import javax.validation.constraints.Size
import org.springframework.format.annotation.NumberFormat

data class CreateRestaurantReviewAppInput(
  @field:NumberFormat(style = NumberFormat.Style.NUMBER) val restaurantId: String,
  @field:NumberFormat(style = NumberFormat.Style.NUMBER) val mountainId: String,
  val title: String,
  val content: String,
  val rating: RestaurantRatingDto,
  @field:Size(max = 10) val images: List<String>,
)
