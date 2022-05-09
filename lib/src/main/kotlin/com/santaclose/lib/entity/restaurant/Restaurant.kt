package com.santaclose.lib.entity.restaurant

import com.santaclose.lib.converter.StringListConverter
import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.location.Location
import javax.persistence.*
import javax.persistence.FetchType.LAZY
import javax.validation.constraints.NotNull

@Entity
class Restaurant(
  @field:NotNull var name: String,
  @Column(length = 100) var description: String,
  @Convert(converter = StringListConverter::class) var images: List<String> = listOf(),
  @OneToMany(fetch = LAZY, mappedBy = "restaurant", cascade = [CascadeType.ALL])
  var restaurantFoodType: MutableList<RestaurantFoodType>,
  @ManyToOne(fetch = LAZY) @field:NotNull var appUser: AppUser,
  @OneToOne(fetch = LAZY) @field:NotNull var location: Location,
) : BaseEntity()
