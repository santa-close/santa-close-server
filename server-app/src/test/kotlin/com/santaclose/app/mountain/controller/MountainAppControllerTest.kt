package com.santaclose.app.mountain.controller

import arrow.core.some
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.auth.security.AppSession
import com.santaclose.app.auth.security.ServerRequestParser
import com.santaclose.app.mountain.controller.dto.CreateMountainAppInput
import com.santaclose.app.mountain.controller.dto.MountainAppSummary
import com.santaclose.app.mountain.service.MountainAppMutationService
import com.santaclose.app.mountain.service.MountainAppQueryService
import com.santaclose.app.mountain.service.dto.MountainSummaryDto
import com.santaclose.app.mountainReview.repository.dto.MountainRatingAverageDto
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountain.type.MountainManagement
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.justRun
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.HttpGraphQlTester

@SpringBootTest
@AutoConfigureHttpGraphQlTester
internal class MountainAppControllerTest(
    private val graphQlTester: HttpGraphQlTester,
    @MockkBean
    private val mountainAppQueryService: MountainAppQueryService,
    @MockkBean
    private val mountainAppMutationService: MountainAppMutationService,
    @MockkBean
    private val serverRequestParser: ServerRequestParser,
) : FreeSpec({

    "Register" - {
        "유저가 산을 등록한다" {
            // given
            val input = CreateMountainAppInput(
                name = "name",
                images = listOf("images"),
                management = MountainManagement.MUNICIPAL,
                altitude = 100,
                longitude = 100.0,
                latitude = 50.0,
                address = "address",
                postcode = "postcode",
            )
            val session = AppSession(123, AppUserRole.USER)

            every { serverRequestParser.parse(any()) } returns session.some()
            justRun { mountainAppMutationService.register(input, session.id) }

            // when
            val response = graphQlTester
                .documentName("registerMountain")
                .variable("input", input)
                .execute()

            // then
            response
                .path("registerMountain")
                .entity(Boolean::class.java)
                .isEqualTo(true)
        }
    }

    "MountainSummary" - {
        "산 요약정보를 가져온다" {
            // given
            val dto =
                MountainSummaryDto(
                    mountain = Mountain(
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
                    ).also { it.id = 100 },
                    restaurantLocations = emptyList(),
                    mountainRating = MountainRatingAverageDto.empty,
                )
            val session = AppSession(123, AppUserRole.USER)

            every { serverRequestParser.parse(any()) } returns session.some()
            coEvery { mountainAppQueryService.findOneSummary(123) } returns dto

            // when
            val response = graphQlTester
                .documentName("mountainSummary")
                .variable("id", session.id)
                .execute()

            // then
            response
                .path("mountainSummary")
                .entity(MountainAppSummary::class.java)
                .satisfies {
                    it.id shouldBe "${dto.mountain.id}"
                    it.address shouldBe "test address"
                    it.rating shouldBe 0.0
                    it.reviewCount shouldBe 0
                }
        }
    }
})
