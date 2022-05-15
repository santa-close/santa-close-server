package com.santaclose.app.restaurant.resolver.dto

import com.santaclose.app.mountainRestaurant.repository.dto.LatestMountainDto
import com.santaclose.app.mountainRestaurant.resolver.dto.LatestMountain
import com.santaclose.app.restaurantReview.repository.dto.LatestRestaurantReviewDto
import com.santaclose.app.restaurantReview.repository.dto.RestaurantRatingAverageDto
import com.santaclose.app.restaurantReview.resolver.dto.LatestRestaurantReview
import com.santaclose.app.restaurantReview.resolver.dto.RestaurantRatingAverage
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurant.type.FoodType

data class RestaurantAppDetail(
  val name: String,
  val address: String,
  val foodType: List<FoodType>,
  val restaurantRatingAverage: RestaurantRatingAverage,
  val restaurantReviews: List<LatestRestaurantReview>,
  val mountains: List<LatestMountain>,
) {
  companion object {
    fun by(
      restaurant: Restaurant,
      averageDto: RestaurantRatingAverageDto,
      restaurantReviewDtos: List<LatestRestaurantReviewDto>,
      mountainDtos: List<LatestMountainDto>,
    ) =
      RestaurantAppDetail(
        restaurant.name,
        restaurant.location.address,
        restaurant.restaurantFoodType.map { it.foodType },
        RestaurantRatingAverage.by(averageDto),
        restaurantReviewDtos.map { LatestRestaurantReview.by(it) },
        mountainDtos.map { LatestMountain.by(it) }
      )
  }
}
