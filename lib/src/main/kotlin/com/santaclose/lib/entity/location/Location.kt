package com.santaclose.lib.entity.location

import com.santaclose.lib.entity.BaseEntity
import javax.persistence.Entity
import javax.validation.constraints.NotNull
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point

@Entity
class Location(
  @field:NotNull var point: Point,
  var address: String,
  @field:NotNull var postcode: String
) : BaseEntity() {
  companion object {
    private val factory = GeometryFactory()

    fun createPoint(longitude: Double, latitude: Double): Point =
      factory.createPoint(Coordinate(longitude, latitude))
  }
}
