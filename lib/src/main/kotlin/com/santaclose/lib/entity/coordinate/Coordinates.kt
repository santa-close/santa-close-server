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
import javax.validation.constraints.NotNull

@Entity
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

        fun create(type: CoordinateType, targetId: Long, longitude: Double, latitude: Double) =
            Coordinates(
                point = factory.createPoint(Coordinate(longitude, latitude)),
                type,
                targetId,
            )
    }
}
