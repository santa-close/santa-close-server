package com.santaclose.app.restaurantReview.resolver.dto

import com.santaclose.lib.entity.restaurantReview.type.PriceComment
import org.springframework.format.annotation.NumberFormat
import javax.validation.Valid
import javax.validation.constraints.Size

data class CreateRestaurantReviewAppInput(
    @field:NumberFormat(style = NumberFormat.Style.NUMBER) val restaurantId: String,
    val title: String,
    val content: String,
    @field:Valid val rating: RestaurantRatingInput,
    var priceComment: PriceComment,
    var priceAverage: Int,
    @field:Size(max = 10) val images: List<String>,
)
