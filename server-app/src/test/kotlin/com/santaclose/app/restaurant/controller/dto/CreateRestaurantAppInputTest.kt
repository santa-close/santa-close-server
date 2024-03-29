package com.santaclose.app.restaurant.controller.dto

import com.santaclose.lib.entity.restaurant.type.FoodType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import jakarta.validation.Validation

internal class CreateRestaurantAppInputTest : StringSpec(
    {
        val validator = Validation.buildDefaultValidatorFactory().validator
        val mountainId = "1"
        val name = "식당 이름"
        val images = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val foodTypes = mutableListOf(FoodType.AMERICAN, FoodType.FOOD_COURT)
        val longitude = 120.01
        val latitude = 60.01
        val address = "서울시 성동구 왕십리로 83-21"
        val postcode = "04769"

        "모든 조건에 알맞는 값을 입력받는다 - 성공" {
            // given
            val dto =
                CreateRestaurantAppInput(
                    mountainIds = listOf(mountainId),
                    name = name,
                    images = images,
                    foodTypes = foodTypes,
                    longitude = longitude,
                    latitude = latitude,
                    address = address,
                    postcode = postcode,
                )

            // when
            // Valid annotation 을 통과한 경우, violations 의 값은 0이고, 실패한 경우 1이다.
            val violations = validator.validate(dto)

            // then
            violations shouldHaveSize 0
        }

        "사진이 없어도 식당을 등록할 수 있다 - 성공" {
            // given
            val dto =
                CreateRestaurantAppInput(
                    mountainIds = listOf(mountainId),
                    name = name,
                    images = emptyList(),
                    foodTypes = foodTypes,
                    longitude = longitude,
                    latitude = latitude,
                    address = address,
                    postcode = postcode,
                )

            // when
            val violations = validator.validate(dto)

            // then
            violations shouldHaveSize 0
        }

        "음식점 사진의 개수가 10개를 초과한 경우 오류가 발생한다 - 실패" {
            val overSizeImages =
                mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")

            // given
            val dto =
                CreateRestaurantAppInput(
                    mountainIds = listOf(mountainId),
                    name = name,
                    images = overSizeImages,
                    foodTypes = foodTypes,
                    longitude = longitude,
                    latitude = latitude,
                    address = address,
                    postcode = postcode,
                )

            // when
            val violations = validator.validate(dto)

            // then
            violations shouldHaveSize 1
            violations.first().propertyPath.toString() shouldBe "images"
        }

        "주소명이 공백이거나 없는 경우 오류가 발생한다 - 실패" {
            // given
            val dto =
                CreateRestaurantAppInput(
                    mountainIds = listOf(mountainId),
                    name = name,
                    images = images,
                    foodTypes = foodTypes,
                    longitude = longitude,
                    latitude = latitude,
                    address = "",
                    postcode = postcode,
                )

            // when
            val violations = validator.validate(dto)

            // then
            violations shouldHaveSize 1
            violations.map { it.propertyPath.toString() shouldBe "address" }
        }

        "우편번호가 공백이거나 없는 경우 오류가 발생한다 - 실패" {
            // given
            val dto =
                CreateRestaurantAppInput(
                    mountainIds = listOf(mountainId),
                    name = name,
                    images = images,
                    foodTypes = foodTypes,
                    longitude = longitude,
                    latitude = latitude,
                    address = address,
                    postcode = "",
                )

            // when
            val violations = validator.validate(dto)

            // then
            violations shouldHaveSize 1
            violations.first().propertyPath.toString() shouldBe "postcode"
        }

        "음식점 음식 유형이 존재하지 않는 경우 오류가 발생한다 - 실패" {
            // given
            val dto =
                CreateRestaurantAppInput(
                    mountainIds = listOf(mountainId),
                    name = name,
                    images = images,
                    foodTypes = emptyList(),
                    longitude = longitude,
                    latitude = latitude,
                    address = address,
                    postcode = postcode,
                )

            // when
            val violations = validator.validate(dto)

            // then
            violations shouldHaveSize 1
            violations.first().propertyPath.toString() shouldBe "foodTypes"
        }
    },
)
