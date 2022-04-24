package com.santaclose.app.location.resolver.dto

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Polygon

data class LocationAppInput(
  val diagonalFrom: AppCoordinate,
  val diagonalTo: AppCoordinate,
) {
  companion object {
    private val factory = GeometryFactory()
  }

  fun toPolygon(): Polygon {
    if (diagonalFrom.latitude == diagonalTo.latitude) {
      throw IllegalArgumentException("latitude 는 서로 달라야 합니다")
    }

    if (diagonalFrom.longitude == diagonalTo.longitude) {
      throw IllegalArgumentException("longitude 는 서로 달라야 합니다")
    }

    return factory.createPolygon(
      arrayOf(
        Coordinate(diagonalFrom.longitude, diagonalFrom.latitude),
        Coordinate(diagonalFrom.longitude, diagonalTo.latitude),
        Coordinate(diagonalTo.longitude, diagonalTo.latitude),
        Coordinate(diagonalTo.longitude, diagonalFrom.latitude),
        Coordinate(diagonalFrom.longitude, diagonalFrom.latitude),
      )
    )
  }
}
