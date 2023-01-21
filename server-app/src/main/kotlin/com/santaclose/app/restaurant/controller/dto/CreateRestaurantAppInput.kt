package com.santaclose.app.restaurant.controller.dto

import com.santaclose.lib.entity.restaurant.type.FoodType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Range

data class CreateRestaurantAppInput(
    @field:NotEmpty val mountainIds: List<String>,
    val name: String,
    @field:Size(max = 10) val images: List<String>,
    @field:NotEmpty val foodTypes: List<FoodType>,
    @field:Range(min = -180, max = 180) val longitude: Double,
    @field:Range(min = -90, max = 90) val latitude: Double,
    @field:NotBlank val address: String,
    @field:NotBlank val postcode: String,
)
