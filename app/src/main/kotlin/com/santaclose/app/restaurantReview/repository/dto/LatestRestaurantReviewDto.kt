package com.santaclose.app.restaurantReview.repository.dto

import com.santaclose.app.restaurantReview.resolver.dto.LatestRestaurantReview
import com.santaclose.lib.web.toID

data class LatestRestaurantReviewDto(
  val id: Long,
  val title: String,
  val content: String,
) {
  fun toDetail() = LatestRestaurantReview(id.toID(), title, content)
}
