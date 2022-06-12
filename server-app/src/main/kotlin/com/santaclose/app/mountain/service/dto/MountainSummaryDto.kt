package com.santaclose.app.mountain.service.dto

import com.santaclose.app.mountainReview.repository.dto.MountainRatingAverageDto
import com.santaclose.app.restaurant.repository.dto.RestaurantLocationDto
import com.santaclose.lib.entity.mountain.Mountain

data class MountainSummaryDto(
    val mountain: Mountain,
    val restaurantLocations: List<RestaurantLocationDto>,
    val mountainRating: MountainRatingAverageDto,
)
