package com.santaclose.app.location.resolver.dto

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Polygon

data class LocationAppInput(
  val nw: AppCoordinate,
  val se: AppCoordinate,
) {
  companion object {
    private val factory = GeometryFactory()
  }

  fun toPolygon(): Polygon =
    factory.createPolygon(
      arrayOf(
        Coordinate(nw.longitude, nw.latitude),
        Coordinate(nw.longitude, se.latitude),
        Coordinate(se.longitude, se.latitude),
        Coordinate(se.longitude, nw.latitude),
        Coordinate(nw.longitude, nw.latitude),
      )
    )
}
