package com.santaclose.app.mountain.controller.dto

import com.santaclose.app.mountainRestaurant.controller.dto.LatestRestaurant
import com.santaclose.app.mountainRestaurant.repository.dto.LatestRestaurantDto
import com.santaclose.app.mountainReview.repository.dto.MountainRatingAverageDto
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainReview.MountainReview

data class MountainAppDetail(
    val name: String,
    val address: String,
    val mountainRatingAverageDto: MountainRatingAverage,
    val mountainReviews: List<LatestMountainReview>,
    val restaurants: List<LatestRestaurant>,
) {
    companion object {
        fun by(
            restaurant: Mountain,
            averageDto: MountainRatingAverageDto,
            restaurantReviewDtos: List<MountainReview>,
            restaurantDtos: List<LatestRestaurantDto>,
        ) =
            MountainAppDetail(
                restaurant.name,
                restaurant.location.address,
                MountainRatingAverage.by(averageDto),
                restaurantReviewDtos.map { LatestMountainReview.by(it) },
                restaurantDtos.map { LatestRestaurant.by(it) },
            )
    }
}
