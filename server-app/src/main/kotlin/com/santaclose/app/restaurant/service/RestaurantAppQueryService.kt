package com.santaclose.app.restaurant.service

import arrow.core.Either
import arrow.core.raise.either
import com.santaclose.app.mountain.repository.MountainAppQueryRepository
import com.santaclose.app.mountainRestaurant.repository.MountainRestaurantAppQueryRepository
import com.santaclose.app.restaurant.controller.dto.RestaurantAppDetail
import com.santaclose.app.restaurant.repository.RestaurantAppQueryRepository
import com.santaclose.app.restaurant.service.dto.RestaurantSummaryDto
import com.santaclose.app.restaurantReview.repository.RestaurantReviewAppQueryRepository
import com.santaclose.lib.web.exception.DomainError
import org.springframework.stereotype.Service

@Service
class RestaurantAppQueryService(
    private val restaurantAppQueryRepository: RestaurantAppQueryRepository,
    private val restaurantReviewAppQueryRepository: RestaurantReviewAppQueryRepository,
    private val mountainRestaurantAppQueryRepository: MountainRestaurantAppQueryRepository,
    private val mountainAppQueryRepository: MountainAppQueryRepository,
) {
    private val restaurantReviewLimit = 5
    private val mountainLimit = 5

    fun findDetail(id: Long): Either<DomainError, RestaurantAppDetail> = either {
        val restaurant = restaurantAppQueryRepository.findOneWithLocation(id).bind()
        val restaurantReviews =
            restaurantReviewAppQueryRepository.findAllByRestaurant(restaurant.id, restaurantReviewLimit).bind()
        val restaurantRatingAverage =
            restaurantReviewAppQueryRepository.findRestaurantRatingAverages(restaurant.id).bind()
        val mountains =
            mountainRestaurantAppQueryRepository.findMountainByRestaurant(restaurant.id, mountainLimit).bind()

        RestaurantAppDetail.by(
            restaurant,
            restaurantRatingAverage,
            restaurantReviews,
            mountains,
        )
    }

    fun findOneSummary(id: Long): Either<DomainError, RestaurantSummaryDto> = either {
        val mountain = restaurantAppQueryRepository.findOneWithLocation(id).bind()
        val locations = mountainAppQueryRepository.findLocationByRestaurant(id).bind()
        val ratings = restaurantReviewAppQueryRepository.findRestaurantRatingAverages(id).bind()

        RestaurantSummaryDto(mountain, locations, ratings)
    }
}
