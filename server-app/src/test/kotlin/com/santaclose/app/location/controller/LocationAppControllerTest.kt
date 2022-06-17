package com.santaclose.app.location.controller

import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.location.controller.dto.MountainAppLocation
import com.santaclose.app.location.controller.enum.LocationType
import com.santaclose.app.location.service.LocationAppQueryService
import com.santaclose.app.util.AppContextMocker
import com.santaclose.app.util.GraphqlBody
import com.santaclose.app.util.gqlRequest
import com.santaclose.app.util.withSuccess
import com.santaclose.lib.entity.appUser.type.AppUserRole
import io.mockk.every
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
internal class LocationAppControllerTest(
    private val webTestClient: WebTestClient,
    @MockkBean private val locationAppQueryService: LocationAppQueryService,
) : AppContextMocker() {
    @Nested
    inner class Locations {
        @Test
        fun `요청한 지역안의 위치 정보를 가져온다`() {
            // given
            val query = GraphqlBody(
                """query {
                |  locations(input: {diagonalFrom: {latitude: 37.5, longitude: 127.5}, diagonalTo: {latitude: 37.6, longitude: 127.6}}) {
                |    id
                |    type
                |    coordinate {
                |      latitude
                |      longitude
                |    }
                |  }
                |}
                """.trimMargin()
            )
            every { locationAppQueryService.find(any()) } returns
                listOf(
                    MountainAppLocation(
                        id = 123,
                        point = GeometryFactory().createPoint(Coordinate(127.5, 35.5))
                    )
                )
            withMockUser(AppUserRole.USER)

            // when
            val response = webTestClient.gqlRequest(query)

            // then
            response.withSuccess("locations") {
                expect("[0].id", false).isEqualTo(123)
                expect("[0].type", false).isEqualTo(LocationType.MOUNTAIN.name)
                expect("[0].coordinate.longitude", false).isEqualTo(127.5)
                expect("[0].coordinate.latitude", false).isEqualTo(35.5)
            }
        }
    }
}
