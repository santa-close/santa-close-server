package com.santaclose.app.auth.context.parser

import com.santaclose.app.auth.context.AppSession
import com.santaclose.app.config.JWTConfig
import com.santaclose.lib.entity.appUser.type.AppUserRole
import io.kotest.assertions.arrow.core.shouldBeNone
import io.kotest.assertions.arrow.core.shouldBeSome
import org.junit.jupiter.api.Test
import org.springframework.mock.web.reactive.function.server.MockServerRequest

internal class ServerRequestParserImplTest {
  private val parser = ServerRequestParserImpl(JWTConfig(JwtTestUtil.secret, 30))

  @Test
  fun `토큰 정보가 없으면 none 을 반환한다`() {
    // given
    val request = MockServerRequest.builder().build()

    // when
    val result = parser.parse(request)

    // then
    result.shouldBeNone()
  }

  @Test
  fun `유효한 토큰이 아니면 none 을 반환한다`() {
    // given
    val request = MockServerRequest.builder().header("Authorization", "Bearer invalid").build()

    // when
    val result = parser.parse(request)

    // then
    result.shouldBeNone()
  }

  @Test
  fun `유효기간이 지난 토큰이면 none 을 반환한다`() {
    // given
    val user = AppSession(12345, AppUserRole.USER)
    val request =
      MockServerRequest.builder()
        .header("Authorization", "Bearer ${JwtTestUtil.genToken(user, true)}")
        .build()

    // when
    val result = parser.parse(request)

    // then
    result.shouldBeNone()
  }

  @Test
  fun `유효한 토큰을 읽어 세션을 반환한다`() {
    // given
    val user = AppSession(12345, AppUserRole.USER)
    val request =
      MockServerRequest.builder()
        .header("Authorization", "Bearer ${JwtTestUtil.genToken(user)}")
        .build()

    // when
    val result = parser.parse(request)

    // then
    result shouldBeSome user
  }
}
