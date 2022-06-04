package com.santaclose.app.location.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.location.resolver.enum.LocationType

interface AppLocation {
    val type: LocationType
    fun id(): ID
    fun coordinate(): AppCoordinate
}
