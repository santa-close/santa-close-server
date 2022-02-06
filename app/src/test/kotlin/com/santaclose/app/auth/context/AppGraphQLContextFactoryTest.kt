package com.santaclose.app.auth.context

import arrow.core.Option
import com.santaclose.lib.entity.appUser.AppUser
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.mock.web.reactive.function.server.MockServerRequest

internal class AppGraphQLContextFactoryTest {
    @Test
    fun `주어진 request 와 사용자 정보를 반환한다`() = runTest {
        // given
        val appGraphQLContextFactory = AppGraphQLContextFactory()
        val request = MockServerRequest.builder().build()

        // when
        val result = appGraphQLContextFactory.generateContextMap(request)

        // then
        result.shouldNotBeNull()
        result["request"] shouldBe request
        result["user"].shouldBeInstanceOf<Option<AppUser>>()
    }
}
