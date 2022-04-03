package com.santaclose.app.restaurantReview.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.lib.entity.restaurantReview.type.PriceComment
import javax.validation.Valid
import javax.validation.constraints.Size
import org.springframework.format.annotation.NumberFormat

data class CreateRestaurantReviewAppInput(
  @field:NumberFormat(style = NumberFormat.Style.NUMBER) val restaurantId: ID,
  val title: String,
  val content: String,
  @field:Valid val rating: RestaurantRatingInput,
  var priceComment: PriceComment,
  var priceAverage: Int,
  @field:Size(max = 10) val images: List<String>,
)
