package com.santaclose.app.restaurantReview.repository

import com.santaclose.lib.entity.restaurantReview.RestaurantReview
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantReviewAppRepository : JpaRepository<RestaurantReview, Long>
