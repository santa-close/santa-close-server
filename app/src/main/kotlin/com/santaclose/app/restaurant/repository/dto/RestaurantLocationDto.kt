package com.santaclose.app.restaurant.repository.dto

import org.locationtech.jts.geom.Geometry

data class RestaurantLocationDto(
    val id: Long,
    val point: Geometry,
)
