package com.santaclose.lib.entity.coordinate

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.coordinate.type.CoordinateType
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Index
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(indexes = [Index(columnList = "targetId")])
class Coordinates(
    @field:NotNull
    var point: Point,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @field:NotNull
    var type: CoordinateType,

    @field:NotNull
    var targetId: Long
) : BaseEntity() {
    companion object {
        private val factory = GeometryFactory()

        fun byMountain(targetId: Long, longitude: Double, latitude: Double) =
            create(CoordinateType.MOUNTAIN, targetId, longitude, latitude)

        fun byRestaurant(targetId: Long, longitude: Double, latitude: Double) =
            create(CoordinateType.RESTAURANT, targetId, longitude, latitude)

        fun create(type: CoordinateType, targetId: Long, longitude: Double, latitude: Double) =
            Coordinates(
                point = factory.createPoint(Coordinate(longitude, latitude)),
                type,
                targetId,
            )
    }
}
