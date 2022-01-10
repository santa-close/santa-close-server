package com.santaclose.app.sample.resolver.dto

import com.querydsl.core.annotations.QueryProjection
import com.santaclose.lib.entity.sample.type.SampleStatus

data class SampleDto @QueryProjection constructor(
    val name: String,
    val price: Int,
    val status: SampleStatus,
)
