package com.santaclose.app.sample.resolver.dto

import com.santaclose.lib.entity.sample.type.SampleStatus

data class ECode(
    val code: SampleStatus,
    val name: String,
)
