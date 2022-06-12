package com.santaclose.app.location.repository.dto

import org.locationtech.jts.geom.Point

interface LocationPointDto {
    val id: Long
    val point: Point
}
