package com.santaclose.app.category.resolver.dto

import com.santaclose.lib.entity.mountainReview.type.MountainDifficulty

data class MountainDifficultyCategory(
    val code: MountainDifficulty,
    val name: String,
) {
    companion object {
        fun category() = MountainDifficulty
            .values()
            .map { MountainDifficultyCategory(it, it.name) }
    }
}
