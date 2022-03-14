package com.santaclose.lib.entity.mountainReview

import com.santaclose.lib.converter.StringListConverter
import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainReview.type.MountainDifficulty
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType.LAZY
import javax.persistence.ManyToOne
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Entity
class MountainReview(
  @field:NotNull var title: String,
  @Valid @Embedded @field:NotNull var rating: MountainRating,
  @Column(columnDefinition = "TEXT") @field:NotNull var content: String,
  @Convert(converter = StringListConverter::class)
  var images: MutableList<String> = mutableListOf(),
  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  @field:NotNull
  var difficulty: MountainDifficulty,
  @ManyToOne(fetch = LAZY) @field:NotNull var mountain: Mountain,
  @ManyToOne(fetch = LAZY) @field:NotNull var appUser: AppUser,
) : BaseEntity() {
  companion object {
    fun create(
      title: String,
      content: String,
      images: List<String>,
      rating: MountainRating,
      difficulty: MountainDifficulty,
      mountain: Mountain,
      appUser: AppUser,
    ): MountainReview =
      MountainReview(
        title,
        rating,
        content,
        images.toMutableList(),
        difficulty,
        mountain,
        appUser,
      )
  }
}
