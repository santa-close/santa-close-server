package com.santaclose.app.location.resolver.dto

import org.hibernate.validator.constraints.Range

data class AppCoordinate(
    @field:Range(min = -180, max = 180) val longitude: Double,
    @field:Range(min = -90, max = 90) val latitude: Double,
)
