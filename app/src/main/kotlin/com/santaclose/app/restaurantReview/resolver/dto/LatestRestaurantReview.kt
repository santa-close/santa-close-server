package com.santaclose.app.restaurantReview.resolver.dto

import com.santaclose.app.restaurantReview.repository.dto.LatestRestaurantReviewDto

data class LatestRestaurantReview(
    val id: String,
    val title: String,
    val content: String,
) {
    companion object {
        fun by(dto: LatestRestaurantReviewDto) =
            LatestRestaurantReview(dto.id.toString(), dto.title, dto.content)
    }
}
