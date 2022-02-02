package com.santaclose.app.auth.directive

import arrow.core.None
import arrow.core.Option
import arrow.core.toOption
import com.expediagroup.graphql.generator.directives.KotlinFieldDirectiveEnvironment
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.appUser.type.AppUserRole
import graphql.GraphQLException
import graphql.GraphqlErrorException
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class AuthSchemaDirectiveWiringTest {
    private val authSchemaDirectiveWiring = AuthSchemaDirectiveWiring()

    @Nested
    inner class OnField {
        @Test
        fun `유효한 파라미터가 아니라면 에러가 발생한다`() {
            // given
            val envMock = mockk<KotlinFieldDirectiveEnvironment>()
            every { envMock.directive.getArgument("role").argumentValue.value } returns "invalid"

            // when
            val exception = shouldThrow<GraphQLException> {
                authSchemaDirectiveWiring.onField(envMock)
            }

            // then
            exception.message shouldBe "invalid app user role: invalid"
        }

        @Test
        fun `익명 사용자가 요청 시 에러가 발생한다`() {
            // given
            val envMock = mockk<KotlinFieldDirectiveEnvironment>(relaxed = true)
            val slot = slot<DataFetcher<Any>>()

            every { envMock.directive.getArgument("role").argumentValue.value } returns AppUserRole.USER
            every { envMock.element } returns mockk()
            every { envMock.setDataFetcher(capture(slot)) } returns Unit

            // when
            authSchemaDirectiveWiring.onField(envMock)

            // then
            val fetchEnvMock = mockk<DataFetchingEnvironment>()
            every { fetchEnvMock.graphQlContext.get<Option<AppUser>>("user") } returns None
            shouldThrow<GraphqlErrorException> {
                slot.captured.get(fetchEnvMock)
            }.apply {
                message shouldBe "접근 권한이 없습니다"
            }
        }

        @Test
        fun `권한없는 사용자가 요청 시 에러가 발생한다`() {
            // given
            val envMock = mockk<KotlinFieldDirectiveEnvironment>(relaxed = true)
            val slot = slot<DataFetcher<Any>>()

            every { envMock.directive.getArgument("role").argumentValue.value } returns AppUserRole.USER
            every { envMock.element } returns mockk()
            every { envMock.setDataFetcher(capture(slot)) } returns Unit

            // when
            authSchemaDirectiveWiring.onField(envMock)

            // then
            val fetchEnvMock = mockk<DataFetchingEnvironment>()
            val appUser = AppUser("11", "id", AppUserRole.VIEWER)
            every { fetchEnvMock.graphQlContext.get<Option<AppUser>>("user") } returns appUser.toOption()

            shouldThrow<GraphqlErrorException> {
                slot.captured.get(fetchEnvMock)
            }.apply {
                message shouldBe "접근 권한이 없습니다"
            }
        }

        @Test
        fun `권한있는 사용자가 요청 시 에러가 발생하지 않는다`() {
            // given
            val envMock = mockk<KotlinFieldDirectiveEnvironment>(relaxed = true)
            val slot = slot<DataFetcher<Any>>()

            every { envMock.directive.getArgument("role").argumentValue.value } returns AppUserRole.USER
            every { envMock.element } returns mockk()
            every { envMock.setDataFetcher(capture(slot)) } returns Unit

            // when
            authSchemaDirectiveWiring.onField(envMock)

            // then
            val fetchEnvMock = mockk<DataFetchingEnvironment>()
            val appUser = AppUser("11", "id", AppUserRole.USER)
            every { fetchEnvMock.graphQlContext.get<Option<AppUser>>("user") } returns appUser.toOption()

            shouldNotThrowAny { slot.captured.get(fetchEnvMock) }
        }
    }
}
