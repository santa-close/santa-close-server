package com.santaclose.lib.entity.mountainReview

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.mountain.Mountain
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne

@Entity
class MountainReview(
    var name: String,
    @Embedded
    var ratings: MountainRating,
    var content: String,
    @ManyToOne(fetch = FetchType.LAZY)
    var mountain: Mountain,
    // 추가한 사용자 id
    // 위치 id
) : BaseEntity()
