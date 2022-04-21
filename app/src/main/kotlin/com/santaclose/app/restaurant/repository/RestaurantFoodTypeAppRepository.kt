package com.santaclose.app.restaurant.repository

import com.santaclose.lib.entity.restaurant.RestaurantFoodType
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantFoodTypeAppRepository : JpaRepository<RestaurantFoodType, Long>
