package com.santaclose.app.sample.controller.dto

import com.santaclose.lib.entity.sample.Sample
import com.santaclose.lib.entity.sample.type.SampleStatus
import jakarta.validation.constraints.Positive

data class CreateSampleAppInput(
    val name: String,
    @field:Positive val price: Int,
    val status: SampleStatus,
) {
    fun toEntity(): Sample = Sample(name = name, price = price, status = status)
}
