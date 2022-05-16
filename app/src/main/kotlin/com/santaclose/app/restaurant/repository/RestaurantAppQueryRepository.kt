package com.santaclose.app.restaurant.repository

import com.santaclose.lib.entity.restaurant.Restaurant

interface RestaurantAppQueryRepository {
  fun findOneWithLocation(id: Long): Restaurant
}
