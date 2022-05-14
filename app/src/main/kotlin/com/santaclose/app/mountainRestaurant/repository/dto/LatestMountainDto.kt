package com.santaclose.app.mountainRestaurant.repository.dto

import com.santaclose.app.mountainRestaurant.resolver.dto.LatestMountain
import com.santaclose.lib.web.toID

data class LatestMountainDto(
  val id: Long,
  val name: String,
) {
  fun toDetail() = LatestMountain(id.toID(), name)
}
