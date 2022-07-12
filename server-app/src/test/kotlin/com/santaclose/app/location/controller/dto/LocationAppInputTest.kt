package com.santaclose.app.location.controller.dto

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Polygon

internal class LocationAppInputTest : StringSpec(
    {
        val rectangle: Polygon =
            GeometryFactory()
                .createPolygon(
                    arrayOf(
                        Coordinate(10.0, 10.0),
                        Coordinate(10.0, 20.0),
                        Coordinate(20.0, 20.0),
                        Coordinate(20.0, 10.0),
                        Coordinate(10.0, 10.0),
                    ),
                )

        "두 좌표의 longitude 가 같으면 에러가 발생한다" {
            // given
            val input =
                LocationAppInput(
                    diagonalFrom = AppCoordinate(longitude = 10.0, latitude = 10.0),
                    diagonalTo = AppCoordinate(longitude = 10.0, latitude = 20.0),
                )

            // when
            val result = shouldThrow<IllegalArgumentException> { input.toPolygon() }

            // then
            result.message shouldBe "longitude 는 서로 달라야 합니다"
        }

        "두 좌표의 latitude 가 같으면 에러가 발생한다" {
            // given
            val input =
                LocationAppInput(
                    diagonalFrom = AppCoordinate(longitude = 10.0, latitude = 10.0),
                    diagonalTo = AppCoordinate(longitude = 20.0, latitude = 10.0),
                )

            // when
            val result = shouldThrow<IllegalArgumentException> { input.toPolygon() }

            // then
            result.message shouldBe "latitude 는 서로 달라야 합니다"
        }

        "좌하단과 우상단 좌표를 제공한 경우" {
            // given
            val input =
                LocationAppInput(
                    diagonalFrom = AppCoordinate(longitude = 10.0, latitude = 10.0),
                    diagonalTo = AppCoordinate(longitude = 20.0, latitude = 20.0),
                )

            // when
            val result = input.toPolygon()

            // then
            result.equals(rectangle) shouldBe true
        }

        "좌상단과 우하단 좌표를 제공한 경우" {
            // given
            val input =
                LocationAppInput(
                    diagonalFrom = AppCoordinate(longitude = 10.0, latitude = 20.0),
                    diagonalTo = AppCoordinate(longitude = 20.0, latitude = 10.0),
                )

            // when
            val result = input.toPolygon()

            // then
            result.equals(rectangle) shouldBe true
        }

        "우상단과 좌하단 좌표를 제공한 경우" {
            // given
            val input =
                LocationAppInput(
                    diagonalFrom = AppCoordinate(longitude = 20.0, latitude = 20.0),
                    diagonalTo = AppCoordinate(longitude = 10.0, latitude = 10.0),
                )

            // when
            val result = input.toPolygon()

            // then
            result.equals(rectangle) shouldBe true
        }

        "우하단과 좌상단 좌표를 제공한 경우" {
            // given
            val input =
                LocationAppInput(
                    diagonalFrom = AppCoordinate(longitude = 20.0, latitude = 10.0),
                    diagonalTo = AppCoordinate(longitude = 10.0, latitude = 20.0),
                )

            // when
            val result = input.toPolygon()

            // then
            result.equals(rectangle) shouldBe true
        }
    },
)
