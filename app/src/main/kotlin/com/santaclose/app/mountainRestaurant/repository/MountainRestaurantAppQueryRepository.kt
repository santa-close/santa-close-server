package com.santaclose.app.mountainRestaurant.repository

import com.santaclose.app.mountainRestaurant.repository.dto.LatestMountainDto

interface MountainRestaurantAppQueryRepository {
    fun findMountainByRestaurant(id: Long, limit: Int): List<LatestMountainDto>
}
