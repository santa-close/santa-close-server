package com.santaclose.app.restaurant.resolver.dto

import com.santaclose.app.mountainRestaurant.repository.dto.LatestMountainDto
import com.santaclose.app.restaurantReview.repository.dto.LatestRestaurantReviewDto
import com.santaclose.app.restaurantReview.repository.dto.RestaurantRatingAverageDto
import com.santaclose.lib.entity.restaurant.type.FoodType

data class RestaurantAppDetail(
  val name: String,
  val address: String,
  val foodType: FoodType,
  val restaurantRatingAverage: RestaurantRatingAverageDto,
  val restaurantReviews: List<LatestRestaurantReviewDto>,
  val mountains: List<LatestMountainDto>,
)
