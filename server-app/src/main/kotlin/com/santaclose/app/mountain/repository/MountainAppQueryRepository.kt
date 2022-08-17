package com.santaclose.app.mountain.repository

import com.santaclose.app.mountain.repository.dto.MountainLocationDto
import com.santaclose.lib.entity.mountain.Mountain

interface MountainAppQueryRepository {
    fun findLocationByRestaurant(restaurantId: Long): List<MountainLocationDto>
    fun findOneWithLocation(id: Long): Mountain
}
