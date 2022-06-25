package com.santaclose.app.mountain.controller.dto

import com.santaclose.app.mountain.service.dto.MountainSummaryDto

data class MountainAppSummary(
    val id: String,
    val imageUrl: String,
    val address: String,
    val rating: Double,
    val reviewCount: Int,
    val locations: List<RestaurantAppCoordinate>
) {
    companion object {
        fun by(dto: MountainSummaryDto): MountainAppSummary =
            MountainAppSummary(
                id = dto.mountain.id.toString(),
                imageUrl = dto.mountain.images.first(),
                address = dto.mountain.location.address,
                rating = dto.mountainRating.average,
                reviewCount = dto.mountainRating.totalCount.toInt(),
                locations = dto.restaurantLocations.map(RestaurantAppCoordinate::by)
            )
    }
}
