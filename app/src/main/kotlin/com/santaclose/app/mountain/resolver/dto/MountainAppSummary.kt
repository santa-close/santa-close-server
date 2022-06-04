package com.santaclose.app.mountain.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.mountain.service.dto.MountainSummaryDto
import com.santaclose.lib.web.toID

data class MountainAppSummary(
    val id: ID,
    val imageUrl: String,
    val address: String,
    val rating: Double,
    val reviewCount: Int,
    val locations: List<RestaurantAppCoordinate>
) {
    companion object {
        fun by(dto: MountainSummaryDto): MountainAppSummary =
            MountainAppSummary(
                id = dto.mountain.id.toID(),
                imageUrl = dto.mountain.images.first(),
                address = dto.mountain.location.address,
                rating = dto.mountainRating.average,
                reviewCount = dto.mountainRating.totalCount.toInt(),
                locations = dto.restaurantLocations.map(RestaurantAppCoordinate::by)
            )
    }
}
