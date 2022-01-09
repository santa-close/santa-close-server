package com.santaclose.app.sample.resolver.dto

import com.querydsl.core.annotations.QueryProjection

data class SampleDto @QueryProjection constructor(
    val name: String,
    val price: Int
)
