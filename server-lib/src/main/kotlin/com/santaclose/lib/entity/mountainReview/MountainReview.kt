package com.santaclose.lib.entity.mountainReview

import com.santaclose.lib.converter.StringListConverter
import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainReview.type.MountainDifficulty
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.ManyToOne
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

@Entity
class MountainReview(
    @field:NotNull
    var title: String,

    @field:Valid
    @Embedded
    @field:NotNull
    var rating: MountainRating,

    @Column(columnDefinition = "TEXT")
    @field:NotNull
    var content: String,

    @Convert(converter = StringListConverter::class)
    var images: MutableList<String> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @field:NotNull
    var difficulty: MountainDifficulty,

    @ManyToOne(fetch = LAZY)
    @field:NotNull
    var mountain: Mountain,

    @ManyToOne(fetch = LAZY)
    @field:NotNull
    var appUser: AppUser,
) : BaseEntity()
