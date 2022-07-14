package com.santaclose.app.restaurantReview.controller.dto

import com.santaclose.app.restaurantReview.repository.dto.RestaurantRatingAverageDto

data class RestaurantRatingAverage(
    val taste: Double,
    val parkingSpace: Double,
    val kind: Double,
    val clean: Double,
    val mood: Double,
    val totalCount: Int,
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
                dto.totalCount.toInt(),
                dto.average,
            )
    }
}
