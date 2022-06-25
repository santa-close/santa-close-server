package com.santaclose.app.mountain.repository

import com.santaclose.app.mountain.repository.dto.MountainLocationIdDto
import com.santaclose.lib.entity.mountain.Mountain
import org.springframework.data.jpa.repository.JpaRepository

interface MountainAppRepository : JpaRepository<Mountain, Long> {
    fun findByLocationIdIn(ids: List<Long>): List<MountainLocationIdDto>
}
