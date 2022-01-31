package com.santaclose.lib.entity.restaurantReview

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.restaurant.Restaurant
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne

@Entity
class RestaurantReview(
    var title: String,

    var content: String,

    @Embedded  
    var rating: RestaurantRating,

    @ManyToOne(fetch = FetchType.LAZY)  
    var restaurant: Restaurant,
) : BaseEntity()
