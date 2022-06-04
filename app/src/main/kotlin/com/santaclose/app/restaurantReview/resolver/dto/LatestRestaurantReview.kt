package com.santaclose.app.restaurantReview.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.restaurantReview.repository.dto.LatestRestaurantReviewDto
import com.santaclose.lib.web.toID

data class LatestRestaurantReview(
    val id: ID,
    val title: String,
    val content: String,
) {
    companion object {
        fun by(dto: LatestRestaurantReviewDto) =
            LatestRestaurantReview(dto.id.toID(), dto.title, dto.content)
    }
}
