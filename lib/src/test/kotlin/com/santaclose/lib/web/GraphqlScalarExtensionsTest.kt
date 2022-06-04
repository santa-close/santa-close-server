package com.santaclose.lib.web

import com.expediagroup.graphql.generator.scalars.ID
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class GraphqlScalarExtensionsTest {
    @Test
    fun `Long 타입을 ID 타입으로 변환한다`() {
        // given
        val id = 123L

        // when
        val result = id.toID()

        // then
        result shouldBe ID("123")
    }

    @Test
    fun `ID 타입을 Long 타입으로 변환한다`() {
        // given
        val id = ID("123")

        // when
        val result = id.toLong()

        // then
        result shouldBe 123L
    }
}
