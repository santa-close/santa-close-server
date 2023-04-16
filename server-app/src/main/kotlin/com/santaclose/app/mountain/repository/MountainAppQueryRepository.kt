package com.santaclose.app.mountain.repository

import arrow.core.Either
import com.santaclose.app.mountain.repository.dto.MountainLocationDto
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.web.exception.DomainError.DBFailure

interface MountainAppQueryRepository {
    fun findLocationByRestaurant(restaurantId: Long): Either<DBFailure, List<MountainLocationDto>>
    fun findOneWithLocation(id: Long): Either<DBFailure, Mountain?>
}
