package com.santaclose.app.restaurant.service

import com.santaclose.app.mountainRestaurant.repository.MountainRestaurantAppQueryRepository
import com.santaclose.app.restaurant.controller.dto.RestaurantAppDetail
import com.santaclose.app.restaurant.repository.RestaurantAppQueryRepository
import com.santaclose.app.restaurantReview.repository.RestaurantReviewAppQueryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class RestaurantAppQueryService(
    private val restaurantAppQueryRepository: RestaurantAppQueryRepository,
    private val restaurantReviewAppQueryRepository: RestaurantReviewAppQueryRepository,
    private val mountainRestaurantAppQueryRepository: MountainRestaurantAppQueryRepository,
) {
    private val restaurantReviewLimit = 5
    private val mountainLimit = 5

    fun findDetail(id: Long): RestaurantAppDetail = runBlocking {
        val restaurant = restaurantAppQueryRepository.findOneWithLocation(id)
        val restaurantReviews =
            async { restaurantReviewAppQueryRepository.findAllByRestaurant(restaurant.id, restaurantReviewLimit) }
        val restaurantRatingAverage =
            async { restaurantReviewAppQueryRepository.findRestaurantRatingAverages(restaurant.id) }
        val mountains =
            async { mountainRestaurantAppQueryRepository.findMountainByRestaurant(restaurant.id, mountainLimit) }

        RestaurantAppDetail.by(
            restaurant,
            restaurantRatingAverage.await(),
            restaurantReviews.await(),
            mountains.await(),
        )
    }
}
