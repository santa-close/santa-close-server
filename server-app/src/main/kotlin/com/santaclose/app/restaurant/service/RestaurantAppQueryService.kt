package com.santaclose.app.restaurant.service

import com.santaclose.app.mountainRestaurant.repository.MountainRestaurantAppQueryRepository
import com.santaclose.app.restaurant.controller.dto.RestaurantAppDetail
import com.santaclose.app.restaurant.repository.RestaurantAppQueryRepository
import com.santaclose.app.restaurantReview.repository.RestaurantReviewAppQueryRepository
import org.springframework.stereotype.Service

@Service
class RestaurantAppQueryService(
    private val restaurantAppQueryRepository: RestaurantAppQueryRepository,
    private val restaurantReviewAppQueryRepository: RestaurantReviewAppQueryRepository,
    private val mountainRestaurantAppQueryRepository: MountainRestaurantAppQueryRepository,
) {
    private val restaurantReviewLimit = 5
    private val mountainLimit = 5

    fun findDetail(id: Long): RestaurantAppDetail {
        val restaurant = restaurantAppQueryRepository.findOneWithLocation(id)
        val restaurantReviews =
            restaurantReviewAppQueryRepository.findAllByRestaurant(restaurant.id, restaurantReviewLimit)
        val restaurantRatingAverage =
            restaurantReviewAppQueryRepository.findRestaurantRatingAverages(restaurant.id)

        val mountains =
            mountainRestaurantAppQueryRepository.findMountainByRestaurant(restaurant.id, mountainLimit)

        return RestaurantAppDetail.by(
            restaurant,
            restaurantRatingAverage,
            restaurantReviews,
            mountains,
        )
    }
}
