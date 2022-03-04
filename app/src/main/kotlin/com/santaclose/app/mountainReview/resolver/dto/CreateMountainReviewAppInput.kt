package com.santaclose.app.mountainReview.resolver.dto

import com.santaclose.lib.entity.mountainReview.type.MountainDifficulty
import org.hibernate.validator.constraints.Range
import org.springframework.format.annotation.NumberFormat
import org.springframework.format.annotation.NumberFormat.Style.NUMBER

data class CreateMountainReviewAppInput(
    @NumberFormat(style = NUMBER)
    val mountainId: String,

    val title: String,

    @field:Range(min = 1, max = 5)
    var scenery: Int,

    @field:Range(min = 1, max = 5)
    var tree: Int,

    @field:Range(min = 1, max = 5)
    var trail: Int,

    @field:Range(min = 1, max = 5)
    var parking: Int,

    @field:Range(min = 1, max = 5)
    var toilet: Int,

    @field:Range(min = 1, max = 5)
    var traffic: Int,

    val content: String,

    val images: List<String>,

    val difficulty: MountainDifficulty,
)
