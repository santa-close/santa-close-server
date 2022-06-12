package com.santaclose.app.location.resolver.dto

import com.santaclose.app.location.resolver.enum.LocationType

interface AppLocation {
    val type: LocationType
    fun id(): String
    fun coordinate(): AppCoordinate
}
