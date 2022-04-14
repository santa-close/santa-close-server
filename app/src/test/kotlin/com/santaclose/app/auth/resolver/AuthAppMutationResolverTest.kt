package com.santaclose.app.auth.resolver

import arrow.core.left
import arrow.core.right
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.auth.context.AppSession
import com.santaclose.app.auth.service.AuthAppService
import com.santaclose.app.util.AppContextMocker
import com.santaclose.app.util.GraphqlBody
import com.santaclose.app.util.gqlRequest
import com.santaclose.app.util.withError
import com.santaclose.app.util.withSuccess
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.web.error.GraphqlErrorCode
import io.kotest.matchers.string.shouldHaveMinLength
import io.mockk.coEvery
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
internal class AuthAppMutationResolverTest
@Autowired
constructor(
  private val webTestClient: WebTestClient,
  @MockkBean private val authAppService: AuthAppService,
) : AppContextMocker() {
  @Nested
  inner class SignIn {
    @Test
    fun `로그인 실패 시 에러 응답을 반환한다`() {
      // given
      val code = "code"
      val query =
        GraphqlBody(
          """mutation {
            |  signIn(input: {code: "$code", type: KAKAO}) {
            |    accessToken
            |    expiredAt
            |  }
            |}""".trimMargin()
        )
      coEvery { authAppService.signIn(code) } returns Exception("error").left()

      // when
      val response = webTestClient.gqlRequest(query)

      // then
      response.withError(GraphqlErrorCode.SERVER_ERROR, "error")
    }

    @Test
    fun `로그인 성공 시 토큰 정보를 반환한다`() {
      // given
      val code = "code"
      val query =
        GraphqlBody(
          """mutation {
            |  signIn(input: {code: "$code", type: KAKAO}) {
            |    accessToken
            |    expiredAt
            |  }
            |}""".trimMargin()
        )
      coEvery { authAppService.signIn(code) } returns AppSession(123, AppUserRole.USER).right()

      // when
      val response = webTestClient.gqlRequest(query)

      // then
      response.withSuccess("signIn") {
        parse<String>("accessToken") shouldHaveMinLength 50
        expect("expiredAt").isNotEmpty
      }
    }
  }
}
