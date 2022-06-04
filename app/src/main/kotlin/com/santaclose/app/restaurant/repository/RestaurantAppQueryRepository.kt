package com.santaclose.app.restaurant.repository

import com.santaclose.app.restaurant.repository.dto.RestaurantLocationDto
import com.santaclose.lib.entity.restaurant.Restaurant

interface RestaurantAppQueryRepository {
    fun findLocationByMountain(mountainId: Long): List<RestaurantLocationDto>
    fun findOneWithLocation(id: Long): Restaurant
}
