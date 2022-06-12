package com.santaclose.app.mountainReview.repository

import com.santaclose.app.mountainReview.repository.dto.MountainRatingAverageDto
import com.santaclose.lib.entity.mountainReview.MountainReview

interface MountainReviewAppQueryRepository {
    fun findAllByMountainId(mountainId: Long, limit: Int): List<MountainReview>
    fun findMountainRatingAverages(mountainId: Long): MountainRatingAverageDto
}
