package com.santaclose.app.mountain.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.mountain.service.dto.MountainSummaryDto
import com.santaclose.lib.web.toID

data class MountainSummary(
  val id: ID,
  val imageUrl: String,
  val address: String,
) {
  companion object {
    fun by(dto: MountainSummaryDto): MountainSummary =
      MountainSummary(
        id = dto.mountain.id.toID(),
        imageUrl = dto.mountain.images.first(),
        address = dto.mountain.location.address,
      )
  }
}
