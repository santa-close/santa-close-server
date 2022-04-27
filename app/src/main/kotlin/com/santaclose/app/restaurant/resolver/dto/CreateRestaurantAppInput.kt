package com.santaclose.app.restaurant.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.lib.entity.restaurant.type.FoodType
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size
import org.springframework.format.annotation.NumberFormat

data class CreateRestaurantAppInput(
  @field:NumberFormat(style = NumberFormat.Style.NUMBER) val mountainId: ID,
  val name: String,
  val description: String,
  // FIXME: 최대 등록 이미지 개수 10개 확정인지?
  @field:Size(max = 10) val images: List<String>,
  @field:NotEmpty val foodTypes: List<FoodType>,
  val longitude: Double,
  val latitude: Double,
  // FIXME: 주소 및 우편번호 필수?
  val address: String,
  val postcode: String
)
