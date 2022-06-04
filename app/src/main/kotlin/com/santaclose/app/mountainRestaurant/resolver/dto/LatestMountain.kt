package com.santaclose.app.mountainRestaurant.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.mountainRestaurant.repository.dto.LatestMountainDto
import com.santaclose.lib.web.toID

data class LatestMountain(val id: ID, val name: String) {
    companion object {
        fun by(
            dto: LatestMountainDto,
        ) = LatestMountain(dto.id.toID(), dto.name)
    }
}
