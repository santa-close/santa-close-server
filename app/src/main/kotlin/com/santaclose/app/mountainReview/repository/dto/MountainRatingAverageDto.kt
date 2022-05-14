package com.santaclose.app.mountainReview.repository.dto

data class MountainRatingAverageDto(
  val scenery: Double,
  val tree: Double,
  val trail: Double,
  val parking: Double,
  val toilet: Double,
  val traffic: Double,
  val totalCount: Long,
) {
  val average: Double
    get() = (scenery + tree + trail + parking + toilet + traffic) / 6

  companion object {
    val empty = MountainRatingAverageDto(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0)
  }
}
