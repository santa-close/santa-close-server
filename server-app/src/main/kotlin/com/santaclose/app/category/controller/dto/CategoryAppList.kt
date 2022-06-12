package com.santaclose.app.category.controller.dto

data class CategoryAppList(
    val mountainDifficulty: List<MountainDifficultyCategory> = MountainDifficultyCategory.category(),
)
