package com.santaclose.app.location.service

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.right
import com.santaclose.app.location.controller.dto.AppLocation
import com.santaclose.app.location.controller.dto.LocationAppInput
import com.santaclose.app.location.controller.dto.MountainAppLocation
import com.santaclose.app.location.controller.dto.RestaurantAppLocation
import com.santaclose.app.location.repository.LocationAppRepository
import com.santaclose.app.location.repository.findIdsByPolygon
import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.mountain.repository.findByLocationIdIns
import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurant.repository.findByLocationIds
import com.santaclose.lib.web.exception.DomainError
import org.springframework.stereotype.Service

@Service
class LocationAppQueryService(
    private val locationAppRepository: LocationAppRepository,
    private val mountainAppRepository: MountainAppRepository,
    private val restaurantAppRepository: RestaurantAppRepository,
) {
    fun find(input: LocationAppInput): Either<DomainError, List<AppLocation>> = either {
        val locations = locationAppRepository.findIdsByPolygon(input.toPolygon()).bind()

        if (locations.isEmpty()) {
            return emptyList<AppLocation>().right()
        }

        val ids = locations.map { it.id }
        val mountains = mountainAppRepository.findByLocationIdIns(ids).bind()
        val restaurants = restaurantAppRepository.findByLocationIds(ids).bind()

        val findPoint = { id: Long ->
            locations.find { it.id == id }?.point ?: raise(DomainError.NotFound("should have location: id=$id"))
        }

        mountains.map { MountainAppLocation.of(it.id, findPoint(it.locationId)) } +
            restaurants.map { RestaurantAppLocation.of(it.id, findPoint(it.locationId)) }
    }
}
