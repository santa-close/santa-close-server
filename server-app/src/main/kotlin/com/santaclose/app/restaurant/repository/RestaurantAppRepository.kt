package com.santaclose.app.restaurant.repository

import arrow.core.Either
import com.santaclose.app.restaurant.repository.dto.RestaurantLocationIdDto
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.web.exception.DomainError.DBFailure
import com.santaclose.lib.web.exception.catchDB
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantAppRepository : JpaRepository<Restaurant, Long> {
    fun findByLocationIdIn(ids: List<Long>): List<RestaurantLocationIdDto>
}

fun RestaurantAppRepository.findByLocationIds(ids: List<Long>): Either<DBFailure, List<RestaurantLocationIdDto>> =
    Either.catchDB { findByLocationIdIn(ids) }

fun RestaurantAppRepository.has(id: Long): Either<DBFailure, Boolean> =
    Either.catchDB { existsById(id) }
