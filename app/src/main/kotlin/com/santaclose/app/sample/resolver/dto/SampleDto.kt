package com.santaclose.app.sample.resolver.dto

import com.santaclose.lib.entity.sample.type.SampleStatus

data class SampleDto(
    val name: String,
    val price: Int,
    val status: SampleStatus,
)
