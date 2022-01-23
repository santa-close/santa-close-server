package com.santaclose.lib.entity.mountainReview

import javax.persistence.Embeddable
import javax.validation.constraints.Size

@Embeddable
class MountainRating(
    @field:Size(min = 0, max = 5)
    var scenery: Byte,
    @field:Size(min = 0, max = 5)
    var facility: Byte,
    @field:Size(min = 0, max = 5)
    var traffic: Byte,
)
