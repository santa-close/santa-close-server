package com.santaclose.app.restaurantReview.controller.dto

import com.santaclose.lib.entity.restaurantReview.type.PriceComment
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import javax.validation.ConstraintViolation
import javax.validation.Validation

internal class CreateRestaurantReviewAppInputTest : StringSpec({
    val validator = Validation.buildDefaultValidatorFactory().validator

    "rating에 6이 포함되어 있으면 에러가 발생한다" {
        // given
        val dto =
            CreateRestaurantReviewAppInput(
                restaurantId = "1",
                title = "title",
                content = "content",
                rating = RestaurantRatingInput(1, 2, 3, 4, 6),
                images = mutableListOf(),
                priceAverage = 10000,
                priceComment = PriceComment.IS_CHEAP
            )

        // when
        val violations: Set<ConstraintViolation<CreateRestaurantReviewAppInput>> =
            validator.validate(dto)

        // then
        violations shouldHaveSize 1
    }

    "images의 길이가 11일 경우 에러가 발생한다" {
        // given
        val dto =
            CreateRestaurantReviewAppInput(
                restaurantId = "1",
                title = "title",
                content = "content",
                rating = RestaurantRatingInput(1, 2, 3, 4, 5),
                images = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"),
                priceAverage = 10000,
                priceComment = PriceComment.IS_CHEAP
            )

        // when
        val violations: Set<ConstraintViolation<CreateRestaurantReviewAppInput>> =
            validator.validate(dto)

        // then
        violations shouldHaveSize 1
    }

    "restaurantId 값은 숫자가 아닐 경우 에러가 발생한다" {
        // given
        val dto =
            CreateRestaurantReviewAppInput(
                restaurantId = "A",
                title = "title",
                content = "content",
                rating = RestaurantRatingInput(1, 2, 3, 4, 5),
                images = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"),
                priceAverage = 10000,
                priceComment = PriceComment.IS_CHEAP
            )

        // when
        val violations: Set<ConstraintViolation<CreateRestaurantReviewAppInput>> =
            validator.validate(dto)

        // then
        violations shouldHaveSize 1
    }

    "모든 validation 통과시 에러가 발생하지 않는다" {
        // given
        val dto =
            CreateRestaurantReviewAppInput(
                restaurantId = "1",
                title = "",
                content = "content",
                rating = RestaurantRatingInput(1, 2, 3, 4, 5),
                images = mutableListOf("1"),
                priceAverage = 10000,
                priceComment = PriceComment.IS_CHEAP
            )

        // when
        val violations: Set<ConstraintViolation<CreateRestaurantReviewAppInput>> =
            validator.validate(dto)

        // then
        violations shouldHaveSize 0
    }
})
