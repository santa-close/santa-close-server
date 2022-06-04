package com.santaclose.lib.entity.restaurant

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.restaurant.type.FoodType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.validation.constraints.NotNull

@Entity
class RestaurantFoodType(
    @ManyToOne(fetch = FetchType.LAZY)
    @field:NotNull
    var restaurant: Restaurant,

    @Enumerated(EnumType.STRING)
    @field:NotNull
    var foodType: FoodType,
) : BaseEntity()
