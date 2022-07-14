package com.santaclose.app.mountain.controller.dto

import com.santaclose.app.mountainReview.repository.dto.MountainRatingAverageDto

data class MountainRatingAverage(
    val scenery: Double,
    val tree: Double,
    val trail: Double,
    val parking: Double,
    val toilet: Double,
    val traffic: Double,
    val totalCount: Int,
    val average: Double,
) {
    companion object {
        fun by(dto: MountainRatingAverageDto) =
            MountainRatingAverage(
                dto.scenery,
                dto.tree,
                dto.trail,
                dto.parking,
                dto.toilet,
                dto.traffic,
                dto.totalCount.toInt(),
                dto.average,
            )
    }
}
