package com.santaclose.app.mountainReview.controller

import arrow.core.some
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.auth.security.AppSession
import com.santaclose.app.auth.security.ServerRequestParser
import com.santaclose.app.mountainReview.controller.dto.CreateMountainReviewAppInput
import com.santaclose.app.mountainReview.service.MountainReviewAppMutationService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.entity.mountainReview.type.MountainDifficulty
import io.kotest.core.spec.style.FreeSpec
import io.mockk.every
import io.mockk.justRun
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.HttpGraphQlTester
import javax.persistence.NoResultException

@SpringBootTest
@AutoConfigureHttpGraphQlTester
internal class MountainReviewAppControllerTest(
    private val graphQlTester: HttpGraphQlTester,
    @MockkBean
    private val mountainReviewAppMutationService: MountainReviewAppMutationService,
    @MockkBean
    private val serverRequestParser: ServerRequestParser,
) : FreeSpec({

    "createMountainReview" - {
        "mountainId 가 유효하지 않으면 에러를 반환한다" {
            // given
            val input = CreateMountainReviewAppInput(
                mountainId = "1",
                title = "title",
                parking = 5,
                scenery = 5,
                toilet = 5,
                traffic = 5,
                trail = 5,
                tree = 5,
                content = "Good~",
                images = emptyList(),
                difficulty = MountainDifficulty.HARD,
            )
            val session = AppSession(123, AppUserRole.USER)

            every { serverRequestParser.parse(any()) } returns session.some()
            every { mountainReviewAppMutationService.register(any(), any()) } throws
                NoResultException("no result")

            // when
            val response = graphQlTester
                .documentName("createMountainReview")
                .variable("input", input)
                .execute()

            // then
            response
                .errors()
                .expect { it.errorType.toString() == "INTERNAL_ERROR" }
        }

        "정상적으로 생성한다" {
            // given
            val input = CreateMountainReviewAppInput(
                mountainId = "1",
                title = "title",
                parking = 5,
                scenery = 5,
                toilet = 5,
                traffic = 5,
                trail = 5,
                tree = 5,
                content = "Good~",
                images = emptyList(),
                difficulty = MountainDifficulty.HARD,
            )
            val session = AppSession(123, AppUserRole.USER)

            every { serverRequestParser.parse(any()) } returns session.some()
            justRun { mountainReviewAppMutationService.register(any(), session.id) }

            // when
            val response = graphQlTester
                .documentName("createMountainReview")
                .variable("input", input)
                .execute()

            // then
            response
                .path("createMountainReview")
                .entity(Boolean::class.java)
                .isEqualTo(true)
        }
    }
})
