package com.santaclose.app.mountain.controller.dto

import com.santaclose.lib.entity.mountainReview.MountainReview

data class LatestMountainReview(
    val id: String,
    val title: String,
    val content: String,
) {
    companion object {
        fun by(dto: MountainReview) =
            LatestMountainReview(dto.id.toString(), dto.title, dto.content)
    }
}
