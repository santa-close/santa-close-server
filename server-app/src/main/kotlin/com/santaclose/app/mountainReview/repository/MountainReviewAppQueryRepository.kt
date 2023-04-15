package com.santaclose.app.mountainReview.repository

import arrow.core.Either
import com.santaclose.app.mountainReview.repository.dto.MountainRatingAverageDto
import com.santaclose.lib.entity.mountainReview.MountainReview
import com.santaclose.lib.web.exception.DomainError.DBFailure

interface MountainReviewAppQueryRepository {
    fun findAllByMountainId(mountainId: Long, limit: Int): Either<DBFailure, List<MountainReview>>
    fun findMountainRatingAverages(mountainId: Long): Either<DBFailure, MountainRatingAverageDto>
}
