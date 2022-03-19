package com.santaclose.lib.entity.location

import com.santaclose.lib.entity.BaseEntity
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.validation.constraints.NotNull
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
class Location(
  @field:NotNull var point: Point,
) : BaseEntity() {
  companion object {
    private val factory = GeometryFactory()

    fun createPoint(longitude: Double, latitude: Double) =
      factory.createPoint(Coordinate(longitude, latitude))
  }
}
