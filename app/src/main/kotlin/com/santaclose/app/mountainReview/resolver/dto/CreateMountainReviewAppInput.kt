package com.santaclose.app.mountainReview.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.lib.entity.mountainReview.type.MountainDifficulty
import org.hibernate.validator.constraints.Range
import org.springframework.format.annotation.NumberFormat
import org.springframework.format.annotation.NumberFormat.Style.NUMBER

data class CreateMountainReviewAppInput(
    @NumberFormat(style = NUMBER) val mountainId: ID,
    val title: String,
    @field:Range(min = 1, max = 5) val scenery: Int,
    @field:Range(min = 1, max = 5) val tree: Int,
    @field:Range(min = 1, max = 5) val trail: Int,
    @field:Range(min = 1, max = 5) val parking: Int,
    @field:Range(min = 1, max = 5) val toilet: Int,
    @field:Range(min = 1, max = 5) val traffic: Int,
    val content: String,
    val images: List<String>,
    val difficulty: MountainDifficulty,
)
