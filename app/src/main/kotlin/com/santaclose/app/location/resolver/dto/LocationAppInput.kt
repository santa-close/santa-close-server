package com.santaclose.app.location.resolver.dto

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Polygon

data class LocationAppInput(
  val northWest: AppCoordinate,
  val southEast: AppCoordinate,
) {
  companion object {
    private val factory = GeometryFactory()
  }

  fun toPolygon(): Polygon =
    factory.createPolygon(
      arrayOf(
        Coordinate(northWest.longitude, northWest.latitude),
        Coordinate(northWest.longitude, southEast.latitude),
        Coordinate(southEast.longitude, southEast.latitude),
        Coordinate(southEast.longitude, northWest.latitude),
        Coordinate(northWest.longitude, northWest.latitude),
      )
    )
}
