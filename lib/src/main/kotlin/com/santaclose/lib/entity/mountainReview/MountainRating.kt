package com.santaclose.lib.entity.mountainReview

import javax.persistence.Embeddable
import javax.validation.constraints.Min

@Embeddable
class MountainRating(
    @field:Min(0)
    var scenery: Byte,
    @field:Min(0)
    var facility: Byte,
    @field:Min(0)
    var traffic: Byte
)
