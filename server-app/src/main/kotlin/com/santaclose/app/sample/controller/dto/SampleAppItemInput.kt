package com.santaclose.app.sample.controller.dto

import jakarta.validation.constraints.Positive

data class SampleAppItemInput(
    @field:Positive val price: Int,
    val name: String? = "",
)
