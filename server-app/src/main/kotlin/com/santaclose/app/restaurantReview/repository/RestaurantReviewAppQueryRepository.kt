package com.santaclose.app.restaurantReview.repository

import arrow.core.Either
import com.santaclose.app.restaurantReview.repository.dto.LatestRestaurantReviewDto
import com.santaclose.app.restaurantReview.repository.dto.RestaurantRatingAverageDto
import com.santaclose.lib.web.exception.DomainError.DBFailure

interface RestaurantReviewAppQueryRepository {
    fun findAllByRestaurant(restaurantId: Long, limit: Int): Either<DBFailure, List<LatestRestaurantReviewDto>>

    fun findRestaurantRatingAverages(restaurantId: Long): Either<DBFailure, RestaurantRatingAverageDto>
}
