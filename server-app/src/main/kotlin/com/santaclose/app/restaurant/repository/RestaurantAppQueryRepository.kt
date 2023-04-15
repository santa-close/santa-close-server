package com.santaclose.app.restaurant.repository

import arrow.core.Either
import com.santaclose.app.restaurant.repository.dto.RestaurantLocationDto
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.web.exception.DomainError.DBFailure

interface RestaurantAppQueryRepository {
    fun findLocationByMountain(mountainId: Long): Either<DBFailure, List<RestaurantLocationDto>>
    fun findOneWithLocation(id: Long): Restaurant
}
