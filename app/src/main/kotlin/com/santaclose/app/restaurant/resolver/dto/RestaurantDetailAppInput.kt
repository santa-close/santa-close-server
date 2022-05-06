package com.santaclose.app.restaurant.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import org.springframework.format.annotation.NumberFormat

data class RestaurantDetailAppInput(
  @NumberFormat(style = NumberFormat.Style.NUMBER) val id: ID,
)
