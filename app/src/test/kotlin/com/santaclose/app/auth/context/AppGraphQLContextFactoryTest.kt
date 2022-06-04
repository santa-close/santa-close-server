package com.santaclose.app.auth.context

import arrow.core.Option
import arrow.core.Some
import com.santaclose.app.auth.context.parser.ServerRequestParser
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.appUser.type.AppUserRole
import io.kotest.assertions.arrow.core.shouldBeSome
import io.kotest.common.runBlocking
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.mock.web.reactive.function.server.MockServerRequest

internal class AppGraphQLContextFactoryTest {
    @Test
    fun `주어진 request 와 사용자 정보를 반환한다`() = runBlocking {
        // given
        val parser = mockk<ServerRequestParser>()
        val session = AppSession(123, AppUserRole.USER)
        every { parser.parse(any()) } returns Some(session)
        val appGraphQLContextFactory = AppGraphQLContextFactory(parser)
        val request = MockServerRequest.builder().build()

        // when
        val result = appGraphQLContextFactory.generateContextMap(request)

        // then
        result.shouldNotBeNull()
        result["request"] shouldBe request
        result["session"].shouldBeInstanceOf<Option<AppUser>>().shouldBeSome(session)
    }
}
