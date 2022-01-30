package com.santaclose.lib.entity.mountainReview

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.mountain.Mountain
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.ManyToOne
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Entity
class MountainReview(
    @NotNull
    var name: String,

    @field:Valid
    @field:Embedded
    @NotNull
    var rating: MountainRating,

    @field:Column(columnDefinition = "TEXT")
    @NotNull
    var content: String,

    @field:ManyToOne(fetch = LAZY)
    @NotNull
    var mountain: Mountain,

    // 추가한 사용자 id,

    // 위치 id,
) : BaseEntity()
