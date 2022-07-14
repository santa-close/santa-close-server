package com.santaclose.app.mountain.controller.dto

import com.santaclose.lib.entity.mountain.type.MountainManagement
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import javax.validation.Validation

internal class CreateMountainAppInputTest : StringSpec(
    {
        val validator = Validation.buildDefaultValidatorFactory().validator

        "등록된 이미지가 최소 1개 이상이어야 한다" {
            // given
            val dto =
                CreateMountainAppInput(
                    name = "name",
                    images = emptyList(),
                    management = MountainManagement.MUNICIPAL,
                    altitude = 1,
                    longitude = 180.0,
                    latitude = 90.0,
                    address = "address",
                    postcode = "postcode",
                )

            // when
            val violations = validator.validate(dto)

            // then
            violations shouldHaveSize 1
            violations.map { it.propertyPath.toString() } shouldContain "images"
        }

        "이름은 빈 공백문자열이 아니여야 한다" {
            // given
            val dto =
                CreateMountainAppInput(
                    name = "  ",
                    images = listOf("test.png"),
                    management = MountainManagement.MUNICIPAL,
                    altitude = 1,
                    longitude = 180.0,
                    latitude = 90.0,
                    address = "address",
                    postcode = "postcode",
                )

            // when
            val violations = validator.validate(dto)

            // then
            violations shouldHaveSize 1
            violations.map { it.propertyPath.toString() } shouldContain "name"
        }

        "해발 고도 값은 1이상 이어야 한다" {
            // given
            val dto =
                CreateMountainAppInput(
                    name = "name",
                    images = listOf("test.png"),
                    management = MountainManagement.MUNICIPAL,
                    altitude = 0,
                    longitude = 180.0,
                    latitude = 90.0,
                    address = "address",
                    postcode = "postcode",
                )

            // when
            val violations = validator.validate(dto)

            // then
            violations shouldHaveSize 1
            violations.map { it.propertyPath.toString() } shouldContain "altitude"
        }

        "경도는 최소 -180 이어야 한다" {
            // given
            val dto =
                CreateMountainAppInput(
                    name = "name",
                    images = listOf("test.png"),
                    management = MountainManagement.MUNICIPAL,
                    altitude = 1,
                    longitude = -181.0,
                    latitude = -90.0,
                    address = "address",
                    postcode = "postcode",
                )

            // when
            val violations = validator.validate(dto)

            // then
            violations shouldHaveSize 1
            violations.map { it.propertyPath.toString() } shouldContain "longitude"
        }

        "위도는 최소 -90 이어야 한다" {
            // given
            val dto =
                CreateMountainAppInput(
                    name = "name",
                    images = listOf("test.png"),
                    management = MountainManagement.MUNICIPAL,
                    altitude = 1,
                    longitude = -180.0,
                    latitude = -91.0,
                    address = "address",
                    postcode = "postcode",
                )

            // when
            val violations = validator.validate(dto)

            // then
            violations shouldHaveSize 1
            violations.map { it.propertyPath.toString() } shouldContain "latitude"
        }

        "유효한 입력값은 검증을 성공한다" {
            // given
            val dto =
                CreateMountainAppInput(
                    name = "name",
                    images = listOf("test.png"),
                    management = MountainManagement.MUNICIPAL,
                    altitude = 1,
                    longitude = 180.0,
                    latitude = 90.0,
                    address = "address",
                    postcode = "postcode",
                )

            // when
            val violations = validator.validate(dto)

            // then
            violations shouldHaveSize 0
        }
    },
)
