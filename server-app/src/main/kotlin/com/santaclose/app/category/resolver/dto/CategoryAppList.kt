package com.santaclose.app.category.resolver.dto

data class CategoryAppList(
    val mountainDifficulty: List<MountainDifficultyCategory> = MountainDifficultyCategory.category(),
)
