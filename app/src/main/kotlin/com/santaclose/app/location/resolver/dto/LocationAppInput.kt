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
        Coordinate(nw.latitude, nw.longitude),
        Coordinate(nw.latitude, nw.longitude),
      )
    )
}
