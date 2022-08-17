package com.santaclose.app.restaurantReview.repository.dto

data class RestaurantRatingAverageDto(
    val taste: Double,
    val parkingSpace: Double,
    val kind: Double,
    val clean: Double,
    val mood: Double,
    val totalCount: Long,
) {
    val average = (taste + parkingSpace + kind + clean + mood) / 5

    companion object {
        val empty = RestaurantRatingAverageDto(0.0, 0.0, 0.0, 0.0, 0.0, 0)
    }
}
