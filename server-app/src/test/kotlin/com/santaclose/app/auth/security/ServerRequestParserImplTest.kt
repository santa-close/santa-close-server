package com.santaclose.app.auth.security

import com.santaclose.app.config.JWTConfig
import com.santaclose.lib.entity.appUser.type.AppUserRole
import io.kotest.assertions.arrow.core.shouldBeNone
import io.kotest.assertions.arrow.core.shouldBeSome
import io.kotest.core.spec.style.StringSpec
import org.springframework.mock.http.server.reactive.MockServerHttpRequest

internal class ServerRequestParserImplTest : StringSpec({
    val parser = ServerRequestParserImpl(JWTConfig(JwtTestUtil.secret, 30))

    "토큰 정보가 없으면 none 을 반환한다" {
        // given
        val request = MockServerHttpRequest
            .get("/")
            .build()

        // when
        val result = parser.parse(request)

        // then
        result.shouldBeNone()
    }

    "유효한 토큰이 아니면 none 을 반환한다" {
        // given
        val request = MockServerHttpRequest
            .get("/")
            .header("Authorization", "Bearer invalid")
            .build()

        // when
        val result = parser.parse(request)

        // then
        result.shouldBeNone()
    }

    "유효기간이 지난 토큰이면 none 을 반환한다" {
        // given
        val user = AppSession(12345, AppUserRole.USER)
        val request =
            MockServerHttpRequest
                .get("/")
                .header("Authorization", "Bearer ${JwtTestUtil.genToken(user, true)}")
                .build()

        // when
        val result = parser.parse(request)

        // then
        result.shouldBeNone()
    }

    "유효한 토큰을 읽어 세션을 반환한다" {
        // given
        val user = AppSession(12345, AppUserRole.USER)
        val request =
            MockServerHttpRequest
                .get("/")
                .header("Authorization", "Bearer ${JwtTestUtil.genToken(user)}")
                .build()

        // when
        val result = parser.parse(request)

        // then
        result shouldBeSome user
    }
})
