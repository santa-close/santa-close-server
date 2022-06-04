package com.santaclose.app.location.service

import com.santaclose.app.location.repository.LocationAppRepository
import com.santaclose.app.location.resolver.dto.AppLocation
import com.santaclose.app.location.resolver.dto.LocationAppInput
import com.santaclose.app.location.resolver.dto.MountainAppLocation
import com.santaclose.app.location.resolver.dto.RestaurantAppLocation
import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class LocationAppQueryService(
    private val locationAppRepository: LocationAppRepository,
    private val mountainAppRepository: MountainAppRepository,
    private val restaurantAppRepository: RestaurantAppRepository,
) {
    fun find(input: LocationAppInput): List<AppLocation> {
        val locations = this.locationAppRepository.findIdsByArea(input.toPolygon())

        if (locations.isEmpty()) {
            return emptyList()
        }

        return runBlocking {
            val ids = locations.map { it.id }
            val mountains = async { mountainAppRepository.findByLocationIdIn(ids) }
            val restaurants = async { restaurantAppRepository.findByLocationIdIn(ids) }

            val findPoint = { id: Long ->
                locations.find { it.id == id }?.point ?: throw Exception("should have location: id=$id")
            }

            mountains.await().map { MountainAppLocation(it.id, findPoint(it.locationId)) } +
                restaurants.await().map { RestaurantAppLocation(it.id, findPoint(it.locationId)) }
        }
    }
}
