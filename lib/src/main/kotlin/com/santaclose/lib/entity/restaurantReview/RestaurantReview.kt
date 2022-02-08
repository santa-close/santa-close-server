package com.santaclose.lib.entity.restaurantReview

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.restaurant.Restaurant
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.ManyToOne
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Entity
class RestaurantReview(
    var title: String,

    var content: String,

    @Valid
    @Embedded
    @field:NotNull
    var rating: RestaurantRating,

    @ManyToOne(fetch = LAZY)
    @field:NotNull
    var restaurant: Restaurant,
) : BaseEntity()
