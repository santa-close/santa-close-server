package com.santaclose.app.mountainRestaurant.controller.dto

import com.santaclose.app.mountainRestaurant.repository.dto.LatestMountainDto

data class LatestMountain(val id: String, val name: String) {
    companion object {
        fun by(
            dto: LatestMountainDto,
        ) = LatestMountain(dto.id.toString(), dto.name)
    }
}
