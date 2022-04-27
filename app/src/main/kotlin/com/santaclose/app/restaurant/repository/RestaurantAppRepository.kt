package com.santaclose.app.restaurant.repository

import com.santaclose.app.restaurant.repository.dto.RestaurantLocationIdDto
import com.santaclose.lib.entity.restaurant.Restaurant
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantAppRepository : JpaRepository<Restaurant, Long> {
  fun findByLocationIdIn(ids: List<Long>): List<RestaurantLocationIdDto>
}
