package com.santaclose.app.restaurant.controller.dto

import com.santaclose.app.location.controller.dto.AppCoordinate
import com.santaclose.app.mountain.repository.dto.MountainLocationDto

data class MountainAppCoordinate(
    val id: String,
    val coordinate: AppCoordinate,
) {

    companion object {
        fun by(dto: MountainLocationDto): MountainAppCoordinate =
            MountainAppCoordinate(
                dto.id.toString(),
                dto.point.coordinate.run { AppCoordinate(longitude = x, latitude = y) },
            )
    }
}
