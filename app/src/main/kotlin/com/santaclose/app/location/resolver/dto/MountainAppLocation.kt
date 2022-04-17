package com.santaclose.app.location.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.location.resolver.enum.LocationType
import com.santaclose.lib.web.toID
import org.locationtech.jts.geom.Point

class MountainAppLocation(
  private val id: Long,
  private val name: String,
  private val point: Point,
) : AppLocation {
  override val type = LocationType.MOUNTAIN

  override fun id(): ID = id.toID()

  override fun name(): String = name

  override fun coordinate(): AppCoordinate = AppCoordinate(latitude = point.x, longitude = point.y)
}
