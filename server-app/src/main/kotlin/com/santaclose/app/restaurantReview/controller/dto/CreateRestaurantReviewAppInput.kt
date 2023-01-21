package com.santaclose.app.restaurantReview.controller.dto

import com.santaclose.lib.entity.restaurantReview.type.PriceComment
import jakarta.validation.Valid
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.NumberFormat

data class CreateRestaurantReviewAppInput(
    @field:NumberFormat(style = NumberFormat.Style.NUMBER) val restaurantId: String,
    val title: String,
    val content: String,
    @field:Valid val rating: RestaurantRatingInput,
    var priceComment: PriceComment,
    var priceAverage: Int,
    @field:Size(max = 10) val images: List<String>,
)
