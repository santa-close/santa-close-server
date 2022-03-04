package com.santaclose.lib.entity.mountainReview

import com.santaclose.lib.converter.StringListConverter
import com.santaclose.lib.entity.BaseEntity
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
    @field:NotNull
    var title: String,

    @Valid
    @Embedded
    @field:NotNull
    var rating: MountainRating,

    @Column(columnDefinition = "TEXT")
    @field:NotNull
    var content: String,

    @ManyToOne(fetch = LAZY)
    @field:NotNull
    var mountain: Mountain,

    @Convert(converter = StringListConverter::class)
    var images: List<String>,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @field:NotNull
    var difficulty: MountainDifficulty,

    // 추가한 사용자 id,
    // 위치 id,
) : BaseEntity() {
    companion object {
        fun create(
            title: String,
            scenery: Byte,
            tree: Byte,
            trail: Byte,
            parking: Byte,
            toilet: Byte,
            traffic: Byte,
            content: String,
            mountain: Mountain,
            images: List<String>,
            difficulty: MountainDifficulty,
        ): MountainReview = MountainReview(
            title,
            MountainRating(scenery, tree, trail, parking, toilet, traffic),
            content,
            mountain,
            images,
            difficulty
        )
    }
}
