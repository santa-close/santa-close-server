package com.santaclose.app.mountain.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.location.resolver.dto.AppCoordinate
import com.santaclose.app.restaurant.repository.dto.RestaurantLocationDto
import com.santaclose.lib.web.toID

data class RestaurantAppCoordinate(
  val id: ID,
  val coordinate: AppCoordinate,
) {

  companion object {
    fun by(dto: RestaurantLocationDto): RestaurantAppCoordinate =
      RestaurantAppCoordinate(
        dto.id.toID(),
        dto.point.coordinate.run { AppCoordinate(longitude = x, latitude = y) }
      )
  }
}
