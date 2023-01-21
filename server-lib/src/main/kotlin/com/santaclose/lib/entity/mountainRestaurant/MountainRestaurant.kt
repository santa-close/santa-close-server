package com.santaclose.lib.entity.mountainRestaurant

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.restaurant.Restaurant
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.validation.constraints.NotNull

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["mountain_id", "restaurant_id"])])
class MountainRestaurant(
    @ManyToOne(fetch = LAZY)
    @field:NotNull
    var mountain: Mountain,

    @ManyToOne(fetch = LAZY)
    @field:NotNull
    var restaurant: Restaurant,
) : BaseEntity()
