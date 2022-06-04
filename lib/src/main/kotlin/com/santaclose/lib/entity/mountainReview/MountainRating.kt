package com.santaclose.lib.entity.mountainReview

import org.hibernate.validator.constraints.Range
import javax.persistence.Embeddable

@Embeddable
class MountainRating(
    @field:Range(min = 1, max = 5)
    var scenery: Byte,

    @field:Range(min = 1, max = 5)
    var tree: Byte,

    @field:Range(min = 1, max = 5)
    var trail: Byte,

    @field:Range(min = 1, max = 5)
    var parking: Byte,

    @field:Range(min = 1, max = 5)
    var toilet: Byte,

    @field:Range(min = 1, max = 5)
    var traffic: Byte,
)
