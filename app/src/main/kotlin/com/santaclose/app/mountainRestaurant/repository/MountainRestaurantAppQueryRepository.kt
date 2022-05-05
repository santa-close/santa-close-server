package com.santaclose.app.mountainRestaurant.repository

import com.santaclose.app.mountainRestaurant.repository.dto.MountainByRestaurantDto

interface MountainRestaurantAppQueryRepository {
  fun findMountainByRestaurant(id: Long, limit: Int): List<MountainByRestaurantDto>
}
