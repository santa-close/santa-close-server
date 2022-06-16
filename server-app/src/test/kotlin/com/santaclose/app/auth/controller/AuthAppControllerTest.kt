package com.santaclose.app.auth.controller

import arrow.core.left
import arrow.core.right
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.auth.controller.dto.SignInAppInput
import com.santaclose.app.auth.controller.dto.SignInType
import com.santaclose.app.auth.security.AppSession
import com.santaclose.app.auth.service.AuthAppService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.string.shouldHaveMinLength
import io.mockk.coEvery
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.HttpGraphQlTester

@SpringBootTest
@AutoConfigureHttpGraphQlTester
internal class AuthAppControllerTest(
    private val graphQlTester: HttpGraphQlTester,
    @MockkBean private val authAppService: AuthAppService,
) : FreeSpec({

    "SignIn" - {
        "로그인 실패 시 에러 응답을 반환한다" {
            // given
            val input = SignInAppInput(code = "code", type = SignInType.KAKAO)
            coEvery { authAppService.signIn(input.code) } returns Exception("error").left()

            // when
            val response = graphQlTester
                .documentName("signIn")
                .variable("input", input)
                .execute()

            // then
            response
                .errors()
                .expect { it.errorType.toString() == "INTERNAL_ERROR" }
        }

        "로그인 성공 시 토큰 정보를 반환한다" {
            // given
            val input = SignInAppInput(code = "code", type = SignInType.KAKAO)
            coEvery { authAppService.signIn(input.code) } returns AppSession(123, AppUserRole.USER).right()

            // when
            val response = graphQlTester
                .documentName("signIn")
                .variable("input", input)
                .execute()

            // then
            response
                .path("signIn.accessToken")
                .entity(String::class.java)
                .satisfies { it shouldHaveMinLength 50 }
        }
    }
})
