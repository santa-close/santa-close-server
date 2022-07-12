package com.santaclose.app.mountain.service

import com.santaclose.app.mountain.controller.dto.MountainAppDetail
import com.santaclose.app.mountain.repository.MountainAppQueryRepository
import com.santaclose.app.mountain.service.dto.MountainSummaryDto
import com.santaclose.app.mountainRestaurant.repository.MountainRestaurantAppQueryRepository
import com.santaclose.app.mountainReview.repository.MountainReviewAppQueryRepository
import com.santaclose.app.restaurant.repository.RestaurantAppQueryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class MountainAppQueryService(
    private val mountainAppQueryRepository: MountainAppQueryRepository,
    private val mountainReviewAppQueryRepository: MountainReviewAppQueryRepository,
    private val restaurantAppQueryRepository: RestaurantAppQueryRepository,
    private val mountainRestaurantAppQueryRepository: MountainRestaurantAppQueryRepository,
) {
    private val restaurantLimit = 5

    fun findDetail(id: String): MountainAppDetail = runBlocking {
        val mountain = mountainAppQueryRepository.findOneWithLocation(id.toLong())
        val mountainReviews = async { mountainReviewAppQueryRepository.findAllByMountainId(mountain.id, 5) }
        val mountainRatingAverageDto =
            async { mountainReviewAppQueryRepository.findMountainRatingAverages(mountain.id) }
        val restaurants =
            async { mountainRestaurantAppQueryRepository.findRestaurantByMountain(mountain.id, restaurantLimit) }

        MountainAppDetail.by(
            mountain,
            mountainRatingAverageDto.await(),
            mountainReviews.await(),
            restaurants.await(),
        )
    }

    fun findOneSummary(mountainId: Long): MountainSummaryDto = runBlocking {
        val mountain = async { mountainAppQueryRepository.findOneWithLocation(mountainId) }
        val locations = async { restaurantAppQueryRepository.findLocationByMountain(mountainId) }
        val ratings = async { mountainReviewAppQueryRepository.findMountainRatingAverages(mountainId) }

        MountainSummaryDto(mountain.await(), locations.await(), ratings.await())
    }
}
