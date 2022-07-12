package com.santaclose.app.mountain.service

import com.santaclose.app.mountain.controller.dto.MountainAppDetail
import com.santaclose.app.mountain.repository.MountainAppQueryRepository
import com.santaclose.app.mountain.service.dto.MountainSummaryDto
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
) {
    fun findDetail(id: String): MountainAppDetail = runBlocking {
        val mountain = mountainAppQueryRepository.findOneWithLocation(id.toLong())
        val mountainReviews = async { mountainReviewAppQueryRepository.findAllByMountainId(mountain.id, 5) }
        val mountainRatingAverageDto =
            async { mountainReviewAppQueryRepository.findMountainRatingAverages(mountain.id) }
        // 음식점 추가 예정

        MountainAppDetail(
            mountain.name,
            mountain.location.address,
            mountainReviews.await(),
            mountainRatingAverageDto.await(),
        )
    }

    fun findOneSummary(mountainId: Long): MountainSummaryDto = runBlocking {
        val mountain = async { mountainAppQueryRepository.findOneWithLocation(mountainId) }
        val locations = async { restaurantAppQueryRepository.findLocationByMountain(mountainId) }
        val ratings = async { mountainReviewAppQueryRepository.findMountainRatingAverages(mountainId) }

        MountainSummaryDto(mountain.await(), locations.await(), ratings.await())
    }
}
