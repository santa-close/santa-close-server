package com.santaclose.app.location.controller

import arrow.core.some
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.auth.security.AppSession
import com.santaclose.app.auth.security.ServerRequestParser
import com.santaclose.app.location.controller.dto.AppCoordinate
import com.santaclose.app.location.controller.dto.LocationAppInput
import com.santaclose.app.location.controller.dto.MountainAppLocation
import com.santaclose.app.location.service.LocationAppQueryService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import io.kotest.core.spec.style.FreeSpec
import io.mockk.every
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.HttpGraphQlTester

@SpringBootTest
@AutoConfigureHttpGraphQlTester
internal class LocationAppControllerTest(
    private val graphQlTester: HttpGraphQlTester,
    @MockkBean
    private val locationAppQueryService: LocationAppQueryService,
    @MockkBean
    private val serverRequestParser: ServerRequestParser,
) : FreeSpec({

    "locations" - {
        "요청한 지역안의 위치 정보를 가져온다" {
            // given
            val input = LocationAppInput(
                diagonalFrom = AppCoordinate(latitude = 37.5, longitude = 126.5),
                diagonalTo = AppCoordinate(latitude = 37.6, longitude = 126.6),
            )
            val location = MountainAppLocation.of(
                id = 123,
                point = GeometryFactory().createPoint(Coordinate(127.5, 35.5))
            )
            val session = AppSession(123, AppUserRole.USER)

            every { serverRequestParser.parse(any()) } returns session.some()
            every { locationAppQueryService.find(any()) } returns listOf(location)

            // when
            val response = graphQlTester
                .documentName("locations")
                .variable("input", input)
                .execute()

            // then
            response
                .path("locations")
                .entityList(MountainAppLocation::class.java)
                .hasSize(1)
                .contains(location)
        }
    }
})
