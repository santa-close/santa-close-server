package com.santaclose.lib.entity.coordinate

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.coordinate.type.LocationType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Index
import javax.persistence.Table
import javax.validation.constraints.NotNull
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point

@Entity
@Table(indexes = [Index(columnList = "targetId")])
class Location(
  @field:NotNull var point: Point,
  @Enumerated(EnumType.STRING) @Column(length = 20) @field:NotNull var type: LocationType,
  @field:NotNull var targetId: Long
) : BaseEntity() {
  companion object {
    private val factory = GeometryFactory()

    fun byMountain(targetId: Long, longitude: Double, latitude: Double) =
      create(LocationType.MOUNTAIN, targetId, longitude, latitude)

    fun byRestaurant(targetId: Long, longitude: Double, latitude: Double) =
      create(LocationType.RESTAURANT, targetId, longitude, latitude)

    fun create(type: LocationType, targetId: Long, longitude: Double, latitude: Double) =
      Location(
        point = factory.createPoint(Coordinate(longitude, latitude)),
        type,
        targetId,
      )
  }
}
