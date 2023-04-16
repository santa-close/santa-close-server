package com.santaclose.app.location.repository

import arrow.core.Either
import com.santaclose.app.location.repository.dto.LocationPointDto
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.web.exception.DomainError
import com.santaclose.lib.web.exception.catchDB
import org.locationtech.jts.geom.Polygon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LocationAppRepository : JpaRepository<Location, Long> {
    @Query("SELECT l.id AS id, l.point AS point FROM Location l WHERE within(l.point, ?1) = true")
    fun findIdsByArea(polygon: Polygon): List<LocationPointDto>
}

fun LocationAppRepository.findIdsByPolygon(polygon: Polygon): Either<DomainError.DBFailure, List<LocationPointDto>> =
    Either.catchDB { findIdsByArea(polygon) }
