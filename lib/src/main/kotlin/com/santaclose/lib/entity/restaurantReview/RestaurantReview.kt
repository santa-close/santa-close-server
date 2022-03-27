package com.santaclose.lib.entity.restaurantReview

import com.santaclose.lib.converter.StringListConverter
import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.restaurant.Restaurant
import javax.persistence.Convert
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
  @Valid @Embedded @field:NotNull var rating: RestaurantRating,
  @Convert(converter = StringListConverter::class)
  var images: MutableList<String> = mutableListOf(),
  @ManyToOne(fetch = LAZY) @field:NotNull var restaurant: Restaurant,
  @ManyToOne(fetch = LAZY) @field:NotNull var mountain: Mountain,
  @ManyToOne(fetch = LAZY) @field:NotNull var appUser: AppUser,
) : BaseEntity()
