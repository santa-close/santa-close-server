package com.santaclose.app.restaurantReview.resolver.dto

import com.santaclose.lib.entity.restaurantReview.RestaurantRating
import io.kotest.matchers.shouldBe
import javax.validation.ConstraintViolation
import javax.validation.Validation
import org.junit.jupiter.api.Test

internal class CreateRestaurantReviewInputTest {
  val validator = Validation.buildDefaultValidatorFactory().validator

  @Test
  fun `rating의 값이 6이 포함되어 있으면 에러가 발생한다`() {
    // given
    val dto =
      CreateRestaurantReviewInput(
        restaurantId = "1",
        mountainId = "1",
        title = "",
        content = "content",
        rating = RestaurantRating(1, 2, 3, 4, 6),
        images = mutableListOf()
      )

    // when
    val violations: Set<ConstraintViolation<CreateRestaurantReviewInput>> = validator.validate(dto)

    // then
    violations.forEach { it.message shouldBe "1에서 5 사이여야 합니다" }
  }

  @Test
  fun `images의 길이가 11일 경우 에러가 발생한다`() {
    // given
    val dto =
      CreateRestaurantReviewInput(
        restaurantId = "1",
        mountainId = "1",
        title = "",
        content = "content",
        rating = RestaurantRating(1, 2, 3, 4, 5),
        images = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")
      )

    // when
    val violations: Set<ConstraintViolation<CreateRestaurantReviewInput>> = validator.validate(dto)

    // then
    violations.forEach { it.message shouldBe "크기가 0에서 10 사이여야 합니다" }
  }
}
