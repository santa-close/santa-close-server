package com.santaclose.app.mountain.controller.dto

import com.santaclose.app.location.controller.dto.AppCoordinate
import com.santaclose.app.restaurant.repository.dto.RestaurantLocationDto

data class RestaurantAppCoordinate(
    val id: String,
    val coordinate: AppCoordinate,
) {

    companion object {
        fun by(dto: RestaurantLocationDto): RestaurantAppCoordinate =
            RestaurantAppCoordinate(
                dto.id.toString(),
                dto.point.coordinate.run { AppCoordinate(longitude = x, latitude = y) }
            )
    }
}
