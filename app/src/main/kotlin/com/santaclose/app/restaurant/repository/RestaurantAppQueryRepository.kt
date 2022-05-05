package com.santaclose.app.restaurant.repository

import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.app.restaurant.repository.dto.RestaurantLocationDto

interface RestaurantAppQueryRepository {
  fun findLocationByMountain(mountainId: Long): List<RestaurantLocationDto>
  fun findOneWithLocation(id: Long): Restaurant
}
