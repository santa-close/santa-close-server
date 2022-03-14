package com.santaclose.lib.entity.restaurantReview

import javax.persistence.Embeddable
import org.hibernate.validator.constraints.Range

@Embeddable
class RestaurantRating(
  @field:Range(min = 1, max = 5) var taste: Byte,
  @field:Range(min = 1, max = 5) var parkingSpace: Byte,
  @field:Range(min = 1, max = 5) var kind: Byte,
  @field:Range(min = 1, max = 5) var clean: Byte,
  @field:Range(min = 1, max = 5) var mood: Byte,
)
