package com.santaclose.app.location.repository

import com.santaclose.lib.entity.location.Location
import org.locationtech.jts.geom.Polygon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LocationAppRepository : JpaRepository<Location, Long> {
  @Query("SELECT l.id FROM Location l WHERE within(l.point, ?1) = true")
  fun findIdsByArea(polygon: Polygon): List<Long>
}
