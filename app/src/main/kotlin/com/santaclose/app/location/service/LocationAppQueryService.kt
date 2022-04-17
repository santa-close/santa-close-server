package com.santaclose.app.location.service

import com.santaclose.app.location.repository.LocationAppRepository
import com.santaclose.app.location.resolver.dto.AppLocation
import com.santaclose.app.location.resolver.dto.LocationAppInput
import com.santaclose.app.location.resolver.dto.MountainAppLocation
import com.santaclose.app.location.resolver.dto.RestaurantAppLocation
import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import org.springframework.stereotype.Service

@Service
class LocationAppQueryService(
  private val locationAppRepository: LocationAppRepository,
  private val mountainAppRepository: MountainAppRepository,
  private val restaurantAppRepository: RestaurantAppRepository,
) {
  fun find(input: LocationAppInput): List<AppLocation> {
    val locations = this.locationAppRepository.findIdsByArea(input.toPolygon())
    val locationIds = locations.map { it.id }

    val mountains = this.mountainAppRepository.findByIdIn(locationIds)
    val restaurants = this.restaurantAppRepository.findByIdIn(locationIds)

    val findPoint = { id: Long ->
      locations.find { it.id == id }?.point ?: throw Exception("should have location: id=$id")
    }
    return mountains.map { MountainAppLocation(it.id, it.name, findPoint(it.id)) } +
      restaurants.map { RestaurantAppLocation(it.id, it.name, findPoint(it.id)) }
  }
}
