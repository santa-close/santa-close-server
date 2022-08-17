package com.santaclose.app.mountain.repository.dto

import org.locationtech.jts.geom.Geometry

data class MountainLocationDto(
    val id: Long,
    val point: Geometry,
)
