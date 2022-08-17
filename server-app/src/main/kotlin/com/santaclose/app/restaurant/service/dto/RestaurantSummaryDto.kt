package com.santaclose.app.restaurant.service.dto

import com.santaclose.app.mountain.repository.dto.MountainLocationDto
import com.santaclose.app.restaurantReview.repository.dto.RestaurantRatingAverageDto
import com.santaclose.lib.entity.restaurant.Restaurant

data class RestaurantSummaryDto(
    val restaurant: Restaurant,
    val mountainLocations: List<MountainLocationDto>,
    val restaurantRating: RestaurantRatingAverageDto,
)
