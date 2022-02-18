package com.santaclose.app.mountainReview.resolver.dto

import org.hibernate.validator.constraints.Range

data class CreateMountainReviewAppInput(
    val mountainId: String,

    val title: String,

    @field:Range(min = 0, max = 5)
    val scenery: Int,

    @field:Range(min = 0, max = 5)
    val facility: Int,

    @field:Range(min = 0, max = 5)
    val traffic: Int,

    val content: String,
)
