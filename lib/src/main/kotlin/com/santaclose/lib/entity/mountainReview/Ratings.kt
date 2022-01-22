package com.santaclose.lib.entity.mountainReview

import javax.persistence.Embeddable

@Embeddable
class Ratings(
    var scenery: Int,
    var facility: Int,
    var traffic: Int
)
