package com.santaclose.app.mountain.controller.dto

import com.santaclose.lib.entity.mountain.type.MountainManagement
import org.hibernate.validator.constraints.Range
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateMountainAppInput(
    @field:NotBlank val name: String,
    @field:Size(min = 1, max = 10) val images: List<String>,
    val management: MountainManagement,
    @field:Min(1) val altitude: Int,
    @field:Range(min = -180, max = 180) val longitude: Double,
    @field:Range(min = -90, max = 90) val latitude: Double,
    @field:NotBlank val address: String,
    @field:NotBlank val postcode: String,
)
