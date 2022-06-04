package com.santaclose.app.mountain.resolver

import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.mountain.service.MountainAppQueryService
import com.santaclose.app.mountain.service.dto.MountainSummaryDto
import com.santaclose.app.mountainReview.repository.dto.MountainRatingAverageDto
import com.santaclose.app.util.AppContextMocker
import com.santaclose.app.util.GraphqlBody
import com.santaclose.app.util.gqlRequest
import com.santaclose.app.util.withSuccess
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountain.type.MountainManagement
import io.mockk.coEvery
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
internal class MountainAppQueryResolverTest @Autowired constructor(
    private val webTestClient: WebTestClient,
    @MockkBean private val mountainAppQueryService: MountainAppQueryService,
) : AppContextMocker() {

    @Nested
    inner class MountainSummary {
        @Test
        fun `산 요약정보를 가져온다`() {
            // given
            val query = GraphqlBody(
                """query {
                |  mountainSummary(id: "123") {
                |    id
                |    address
                |    imageUrl
                |    rating
                |    reviewCount
                |    locations {
                |      id
                |      coordinate {
                |        latitude
                |        longitude
                |      }
                |    }
                |  }
                |}
                """.trimMargin()
            )
            withMockUser(AppUserRole.USER)
            val dto =
                MountainSummaryDto(
                    mountain =
                    Mountain(
                        name = "test mountain",
                        images = mutableListOf("test.jpg"),
                        management = MountainManagement.MUNICIPAL,
                        altitude = 1000,
                        appUser = AppUser("name", "email", "socialId", AppUserRole.USER),
                        location =
                        Location.create(
                            longitude = 10.0,
                            latitude = 10.0,
                            address = "test address",
                            postcode = "123-1234",
                        ),
                    ),
                    restaurantLocations = emptyList(),
                    mountainRating = MountainRatingAverageDto.empty,
                )

            coEvery { mountainAppQueryService.findOneSummary(123) } returns dto

            // when
            val result = webTestClient.gqlRequest(query)

            // then
            result.withSuccess("mountainSummary") {
                expect("address").isEqualTo("test address")
                expect("reviewCount").isEqualTo(0)
                expect("rating").isEqualTo(0)
            }
        }
    }
}
