package com.santaclose.lib.entity.restaurant

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.restaurant.type.FoodType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotNull

@Entity
class RestaurantFoodType(
    @ManyToOne(fetch = FetchType.LAZY)
    @field:NotNull
    var restaurant: Restaurant,

    @Enumerated(EnumType.STRING)
    @field:NotNull
    var foodType: FoodType,
) : BaseEntity()
