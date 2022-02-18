package com.santaclose.lib.entity.mountainReview

import org.hibernate.validator.constraints.Range
import javax.persistence.Embeddable

@Embeddable
class MountainRating(
    @field:Range(min = 0, max = 5)
    var scenery: Byte,

    @field:Range(min = 0, max = 5)
    var facility: Byte,

    @field:Range(min = 0, max = 5)
    var traffic: Byte,
)
