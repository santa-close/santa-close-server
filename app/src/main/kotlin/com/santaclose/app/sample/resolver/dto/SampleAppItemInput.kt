package com.santaclose.app.sample.resolver.dto

import javax.validation.constraints.Positive

data class SampleAppItemInput(
  @field:Positive val price: Int,
  val name: String? = "",
)
