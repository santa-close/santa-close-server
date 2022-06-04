package com.santaclose.app.mountain.repository

import com.santaclose.lib.entity.mountain.Mountain

interface MountainAppQueryRepository {
    fun findOneWithLocation(id: Long): Mountain
}
