package com.santaclose.app.mountainRestaurant.controller.dto

import com.santaclose.app.mountainRestaurant.repository.dto.LatestRestaurantDto

data class LatestRestaurant(val id: String, val name: String) {
    companion object {
        fun by(dto: LatestRestaurantDto) = LatestRestaurant(dto.id.toString(), dto.name)
    }
}
