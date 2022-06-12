package com.santaclose.app.mountain.controller.dto

import com.santaclose.app.mountainReview.repository.dto.MountainRatingAverageDto
import com.santaclose.lib.entity.mountainReview.MountainReview

data class MountainAppDetail(
    val name: String,
    val address: String,
    val mountainReviews: List<MountainReview>,
    val mountainRatingAverageDto: MountainRatingAverageDto
)
