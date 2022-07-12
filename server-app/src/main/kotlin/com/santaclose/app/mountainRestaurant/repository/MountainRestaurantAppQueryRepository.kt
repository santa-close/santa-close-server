package com.santaclose.app.mountainRestaurant.repository

import com.santaclose.app.mountainRestaurant.repository.dto.LatestMountainDto
import com.santaclose.app.mountainRestaurant.repository.dto.LatestRestaurantDto

interface MountainRestaurantAppQueryRepository {
    fun findMountainByRestaurant(id: Long, limit: Int): List<LatestMountainDto>

    fun findRestaurantByMountain(id: Long, limit: Int): List<LatestRestaurantDto>
}
