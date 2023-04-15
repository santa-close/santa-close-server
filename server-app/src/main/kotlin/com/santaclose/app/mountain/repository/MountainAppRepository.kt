package com.santaclose.app.mountain.repository

import arrow.core.Either
import com.santaclose.app.mountain.repository.dto.MountainLocationIdDto
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.web.exception.DomainError.DBFailure
import com.santaclose.lib.web.exception.catchDB
import org.springframework.data.jpa.repository.JpaRepository

interface MountainAppRepository : JpaRepository<Mountain, Long> {
    fun findByLocationIdIn(ids: List<Long>): List<MountainLocationIdDto>
}

fun MountainAppRepository.findByLocationIdIns(ids: List<Long>): Either<DBFailure, List<MountainLocationIdDto>> =
    Either.catchDB { findByLocationIdIn(ids) }
