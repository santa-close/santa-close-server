package com.santaclose.app.mountain.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import org.springframework.format.annotation.NumberFormat

data class MountainDetailAppInput(
  @NumberFormat(style = NumberFormat.Style.NUMBER) val id: ID,
)
