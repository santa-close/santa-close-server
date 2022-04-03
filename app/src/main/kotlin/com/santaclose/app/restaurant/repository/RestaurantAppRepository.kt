package com.santaclose.app.restaurant.repository

import com.santaclose.lib.entity.restaurant.Restaurant
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantAppRepository : JpaRepository<Restaurant, Long>
