package com.santaclose.app.auth.context.parser

import io.kotest.assertions.arrow.core.shouldBeNone
import org.junit.jupiter.api.Test
import org.springframework.mock.web.reactive.function.server.MockServerRequest

internal class ServerRequestParserImplTest {
    @Test
    fun `토큰 정보가 없으면 none 을 반환한다`() {
        // given
        val parser = ServerRequestParserImpl()
        val request = MockServerRequest.builder().build()

        // when
        val result = parser.parse(request)

        // then
        result.shouldBeNone()
    }

    @Test
    fun `유효한 토큰이 아니면 none 을 반환한다`() {
        // given
        val parser = ServerRequestParserImpl()
        val request = MockServerRequest
            .builder()
            .header("Authorization", "Bearer invalid")
            .build()

        // when
        val result = parser.parse(request)

        // then
        result.shouldBeNone()
    }
}
