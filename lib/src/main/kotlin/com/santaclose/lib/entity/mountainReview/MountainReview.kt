package com.santaclose.lib.entity.mountainReview

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.mountain.Mountain
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.ManyToOne
import javax.validation.Valid

@Entity
class MountainReview(
    var name: String,

    @field:Valid
    @Embedded
    var rating: MountainRating,

    @Column(columnDefinition = "TEXT")
    var content: String,

    @ManyToOne(fetch = LAZY)
    var mountain: Mountain,

    // 추가한 사용자 id,

    // 위치 id,
) : BaseEntity()
