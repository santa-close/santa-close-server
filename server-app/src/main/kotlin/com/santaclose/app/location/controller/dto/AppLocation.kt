package com.santaclose.app.location.controller.dto

import com.santaclose.app.location.controller.enum.LocationType

interface AppLocation {
    val type: LocationType
    val id: String
    val coordinate: AppCoordinate
}
