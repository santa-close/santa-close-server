package com.santaclose.lib.entity.restaurantReview

import javax.persistence.Embeddable
import javax.validation.constraints.Size

@Embeddable
class RestaurantRating(
    @field:Size(min = 0, max = 5)
    var taste: Byte,

    @field:Size(min = 0, max = 5)
    var parkingSpace: Byte,

    @field:Size(min = 0, max = 5)
    var kind: Byte,

    @field:Size(min = 0, max = 5)
    var clean: Byte,

    @field:Size(min = 0, max = 5)
    var mood: Byte,
)
