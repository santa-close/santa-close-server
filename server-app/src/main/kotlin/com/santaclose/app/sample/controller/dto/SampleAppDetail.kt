package com.santaclose.app.sample.controller.dto

import com.santaclose.lib.entity.sample.type.SampleStatus

data class SampleAppDetail(
    val name: String,
    val price: Int,
    val status: SampleStatus,
)
