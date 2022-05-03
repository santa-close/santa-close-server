package com.santaclose.lib.entity.restaurant

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.restaurant.type.FoodType
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class RestaurantFoodType(
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id")
  @field:NotNull
  var restaurant: Restaurant?,
  @Enumerated(EnumType.STRING) @field:NotNull var foodType: FoodType,
) : BaseEntity()
