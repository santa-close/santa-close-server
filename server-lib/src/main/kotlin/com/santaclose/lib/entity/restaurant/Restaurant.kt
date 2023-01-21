package com.santaclose.lib.entity.restaurant

import com.santaclose.lib.converter.StringListConverter
import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.location.Location
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.validation.constraints.NotNull

@Entity
class Restaurant(
    @field:NotNull
    var name: String,

    @Column(length = 100)
    var description: String,

    @Convert(converter = StringListConverter::class)
    var images: List<String> = emptyList(),

    @OneToMany(fetch = LAZY, mappedBy = "restaurant")
    var restaurantFoodType: MutableList<RestaurantFoodType> = mutableListOf(),

    @ManyToOne(fetch = LAZY)
    @field:NotNull
    var appUser: AppUser,

    @OneToOne(fetch = LAZY, cascade = [CascadeType.PERSIST])
    @field:NotNull
    var location: Location,
) : BaseEntity()
