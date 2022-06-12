package com.santaclose.app.location.resolver.dto

import com.santaclose.app.location.resolver.enum.LocationType
import org.locationtech.jts.geom.Point

class MountainAppLocation(
    private val id: Long,
    private val point: Point,
) : AppLocation {
    override val type = LocationType.MOUNTAIN

    override fun id(): String = id.toString()

    override fun coordinate(): AppCoordinate = AppCoordinate(longitude = point.x, latitude = point.y)
}
