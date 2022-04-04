package com.santaclose.lib.entity.mountainRestaurant

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.restaurant.Restaurant
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.constraints.NotNull

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["mountain_id", "restaurant_id"])])
class MountainRestaurant(
  @ManyToOne(fetch = LAZY) @field:NotNull var mountain: Mountain,
  @ManyToOne(fetch = LAZY) @field:NotNull var restaurant: Restaurant,
) : BaseEntity()
