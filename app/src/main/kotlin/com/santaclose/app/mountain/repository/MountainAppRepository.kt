package com.santaclose.app.mountain.repository

import com.santaclose.lib.entity.mountain.Mountain
import org.springframework.data.jpa.repository.JpaRepository

interface MountainAppRepository : JpaRepository<Mountain, Long> {
  fun findByIdIn(ids: List<Long>): List<Mountain>
}
