package com.santaclose.app.mountain.service

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import com.santaclose.app.mountain.controller.dto.MountainAppDetail
import com.santaclose.app.mountain.repository.MountainAppQueryRepository
import com.santaclose.app.mountain.service.dto.MountainSummaryDto
import com.santaclose.app.mountainRestaurant.repository.MountainRestaurantAppQueryRepository
import com.santaclose.app.mountainReview.repository.MountainReviewAppQueryRepository
import com.santaclose.app.restaurant.repository.RestaurantAppQueryRepository
import com.santaclose.lib.web.exception.DomainError
import org.springframework.stereotype.Service

@Service
class MountainAppQueryService(
    private val mountainAppQueryRepository: MountainAppQueryRepository,
    private val mountainReviewAppQueryRepository: MountainReviewAppQueryRepository,
    private val restaurantAppQueryRepository: RestaurantAppQueryRepository,
    private val mountainRestaurantAppQueryRepository: MountainRestaurantAppQueryRepository,
) {
    private val restaurantLimit = 5

    fun findDetail(id: String): Either<DomainError, MountainAppDetail> = either {
        val mountain = mountainAppQueryRepository.findOneWithLocation(id.toLong()).bind()

        ensureNotNull(mountain) { DomainError.NotFound("산이 존재하지 않습니다: $id") }

        val mountainReviews = mountainReviewAppQueryRepository.findAllByMountainId(mountain.id, 5).bind()
        val mountainRatingAverageDto = mountainReviewAppQueryRepository.findMountainRatingAverages(mountain.id).bind()
        val restaurants =
            mountainRestaurantAppQueryRepository.findRestaurantByMountain(mountain.id, restaurantLimit).bind()

        MountainAppDetail.by(
            mountain,
            mountainRatingAverageDto,
            mountainReviews,
            restaurants,
        )
    }

    fun findOneSummary(id: Long): Either<DomainError, MountainSummaryDto> = either {
        val mountain = mountainAppQueryRepository.findOneWithLocation(id).bind()

        ensureNotNull(mountain) { DomainError.NotFound("산이 존재하지 않습니다: $id") }

        val locations = restaurantAppQueryRepository.findLocationByMountain(id).bind()
        val ratings = mountainReviewAppQueryRepository.findMountainRatingAverages(id).bind()

        MountainSummaryDto(mountain, locations, ratings)
    }
}
