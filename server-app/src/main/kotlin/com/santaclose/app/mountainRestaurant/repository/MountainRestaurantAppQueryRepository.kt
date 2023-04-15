package com.santaclose.app.mountainRestaurant.repository

import arrow.core.Either
import com.santaclose.app.mountainRestaurant.repository.dto.LatestMountainDto
import com.santaclose.app.mountainRestaurant.repository.dto.LatestRestaurantDto
import com.santaclose.lib.web.exception.DomainError.DBFailure

interface MountainRestaurantAppQueryRepository {
    fun findMountainByRestaurant(id: Long, limit: Int): List<LatestMountainDto>

    fun findRestaurantByMountain(id: Long, limit: Int): Either<DBFailure, List<LatestRestaurantDto>>
}
