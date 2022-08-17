package com.santaclose.app.restaurant.controller.dto

import com.santaclose.app.restaurant.service.dto.RestaurantSummaryDto

data class RestaurantAppSummary(
    val id: String,
    val imageUrl: String,
    val address: String,
    val rating: Double,
    val reviewCount: Int,
    val locations: List<MountainAppCoordinate>,
) {
    companion object {
        fun by(dto: RestaurantSummaryDto): RestaurantAppSummary =
            RestaurantAppSummary(
                id = dto.restaurant.id.toString(),
                imageUrl = dto.restaurant.images.first(),
                address = dto.restaurant.location.address,
                rating = dto.restaurantRating.average,
                reviewCount = dto.restaurantRating.totalCount.toInt(),
                locations = dto.mountainLocations.map(MountainAppCoordinate::by),
            )
    }
}
