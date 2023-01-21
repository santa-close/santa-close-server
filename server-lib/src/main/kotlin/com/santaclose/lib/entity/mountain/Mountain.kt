package com.santaclose.lib.entity.mountain

import com.santaclose.lib.converter.StringListConverter
import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.type.MountainManagement
import com.santaclose.lib.entity.mountainRestaurant.MountainRestaurant
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.validation.constraints.NotNull

@Entity
class Mountain(
    @field:NotNull
    var name: String,

    @Convert(converter = StringListConverter::class)
    var images: MutableList<String> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var management: MountainManagement,

    @field:NotNull
    var altitude: Int,

    @ManyToOne(fetch = LAZY)
    @field:NotNull
    var appUser: AppUser,

    @OneToOne(fetch = LAZY, cascade = [CascadeType.PERSIST])
    var location: Location,

    @OneToMany(fetch = LAZY, mappedBy = "mountain")
    var mountainRestaurant: MutableList<MountainRestaurant> = mutableListOf(),
) : BaseEntity()
