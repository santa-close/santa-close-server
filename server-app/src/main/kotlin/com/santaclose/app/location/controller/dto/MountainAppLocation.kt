package com.santaclose.app.location.controller.dto

import com.santaclose.app.location.controller.enum.LocationType
import org.locationtech.jts.geom.Point

data class MountainAppLocation(
    override val type: LocationType,
    override val id: String,
    override val coordinate: AppCoordinate,
) : AppLocation {

    companion object {

        fun of(id: Long, point: Point) = MountainAppLocation(
            type = LocationType.MOUNTAIN,
            id = id.toString(),
            coordinate = AppCoordinate(longitude = point.x, latitude = point.y),
        )
    }
}
