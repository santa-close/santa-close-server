package com.santaclose.lib.entity.restaurantReview

import com.santaclose.lib.converter.StringListConverter
import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurantReview.type.PriceComment
import jakarta.persistence.Convert
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.ManyToOne
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

@Entity
class RestaurantReview(
    var title: String,

    var content: String,

    @Enumerated(EnumType.STRING)
    @field:NotNull
    var priceComment: PriceComment,

    @field:NotNull
    var priceAverage: Int,

    @Valid
    @Embedded
    @field:NotNull
    var rating: RestaurantRating,

    @Convert(converter = StringListConverter::class)
    var images: MutableList<String> = mutableListOf(),

    @ManyToOne(fetch = LAZY)
    @field:NotNull
    var restaurant: Restaurant,

    @ManyToOne(fetch = LAZY)
    @field:NotNull
    var appUser: AppUser,
) : BaseEntity()
