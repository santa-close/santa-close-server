package com.santaclose.app.restaurant.resolver.dto

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.lib.entity.restaurant.type.FoodType
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import javax.validation.ConstraintViolation
import javax.validation.Validation
import org.junit.jupiter.api.Test

internal class CreateRestaurantAppInputTest {
  private val validator = Validation.buildDefaultValidatorFactory().validator

  //  @Test
  //  fun `위도 및 경도 좌표가 존재하지 않는 좌표값인 경우 오류가 발생한다 - 실패`() {
  //    // given
  //    val dto =
  //      CreateRestaurantAppInput(
  //        mountainId = ID("1"),
  //        name = "식당 이름",
  //        description = "식당 설명",
  //        images = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
  // "13"),
  //        foodTypes = mutableListOf(FoodType.AMERICAN, FoodType.FOOD_COURT, FoodType.ASIA),
  //        longitude = 120.01,
  //        latitude = 60.01,
  //        address = "서울시 성동구 왕십리로 83-21",
  //        postcode = "04769"
  //      )
  //
  //    // when
  //    val result = input.toPolygon()
  //
  //    // then
  //    result.equals(rectangle) shouldBe true
  //  }

  @Test
  fun `음식점 사진의 개수가 10개를 초과한 경우 오류가 발생한다 - 실패`() {
    // given
    val dto =
      CreateRestaurantAppInput(
        mountainId = ID("1"),
        name = "식당 이름",
        description = "식당 설명",
        images = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"),
        foodTypes = mutableListOf(FoodType.AMERICAN, FoodType.FOOD_COURT, FoodType.ASIA),
        longitude = 120.01,
        latitude = 60.01,
        address = "서울시 성동구 왕십리로 83-21",
        postcode = "04769"
      )

    // when
    // Valid annotation 을 통과한 경우, violations 의 값은 0이고, 실패한 경우 1이다.
    val violations: Set<ConstraintViolation<CreateRestaurantAppInput>> = validator.validate(dto)

    // then
    violations shouldHaveSize 1
  }

  @Test
  fun `음식점 음식 유형이 존재하지 않는 경우 오류가 발생한다 - 실패`() {
    // given
    val dto =
      CreateRestaurantAppInput(
        mountainId = ID("1"),
        name = "식당 이름",
        description = "식당 설명",
        images = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
        foodTypes = emptyList(),
        longitude = 120.01,
        latitude = 60.01,
        address = "서울시 성동구 왕십리로 83-21",
        postcode = "04769"
      )

    // when
    // Valid annotation 을 통과한 경우, violations 의 값은 0이고, 실패한 경우 1이다.
    val violations: Set<ConstraintViolation<CreateRestaurantAppInput>> = validator.validate(dto)

    // then
    // FIXME: 세부적인 오류 메세지 유형 검증까지? 아니면 shouldHaveSize 1만?
    violations shouldHaveSize 1
    violations.forEach {
      it.messageTemplate shouldBe "{javax.validation.constraints.NotEmpty.message}"
    }
  }
}
