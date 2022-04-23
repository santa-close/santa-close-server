package com.santaclose.app.location.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.location.resolver.enum.LocationType
import com.santaclose.lib.web.toID
import org.locationtech.jts.geom.Point

class RestaurantAppLocation(
  private val id: Long,
  private val point: Point,
) : AppLocation {
  override val type = LocationType.RESTAURANT

  override fun id(): ID = id.toID()

  override fun coordinate(): AppCoordinate = AppCoordinate(longitude = point.x, latitude = point.y)
}
