package com.santaclose.app.sample.resolver.dto

import com.santaclose.lib.entity.sample.type.SampleStatus

data class CategoryDto(
    val sampleStatus: List<ECode> = listOf(
        ECode(SampleStatus.OPEN, SampleStatus.OPEN.text)
    )
)
