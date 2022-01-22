package com.santaclose.lib.entity.mountainReview

import javax.persistence.Embeddable

@Embeddable
class MountainRating(
    var scenery: Int,
    var facility: Int,
    var traffic: Int
)
