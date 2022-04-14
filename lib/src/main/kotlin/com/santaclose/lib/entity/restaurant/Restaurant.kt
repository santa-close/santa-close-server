package com.santaclose.lib.entity.restaurant

import com.santaclose.lib.converter.StringListConverter
import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.restaurant.type.FoodType
import javax.persistence.*
import javax.persistence.FetchType.LAZY
import javax.validation.constraints.NotNull

@Entity
class Restaurant(
  @field:NotNull var name: String,
  @Column(length = 100) var description: String,
  @Convert(converter = StringListConverter::class)
  var images: MutableList<String> = mutableListOf(),
  @Enumerated(EnumType.STRING) @field:NotNull var foodType: FoodType,
  @ManyToOne(fetch = LAZY) @field:NotNull var appUser: AppUser,
  @OneToOne(fetch = LAZY) var location: Location,
) : BaseEntity()
