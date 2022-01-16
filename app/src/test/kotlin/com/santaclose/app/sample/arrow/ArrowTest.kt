package com.santaclose.app.sample.arrow

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.computations.option
import arrow.core.filterOption
import arrow.core.some
import io.kotest.assertions.arrow.core.shouldBeSome
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ArrowTest {
    @Nested
    inner class OptionTest {
        @Test
        fun chaining() = runTest {
            // given
            class Bar(val number: Int)
            class Foo(val bar: Option<Bar>)

            fun getFoo() = Foo(
                Option.fromNullable(
                    Bar(50)
                )
            ).some()

            fun divideBy100(num: Int) = when (num) {
                0 -> None
                else -> Some(100 / num)
            }

            // val result = getFoo()
            //     .flatMap { it.bar }
            //     .flatMap { divideBy100(it.number) }
            //     .map { it + 100 }

            // val result = listOf(1, 2, 3, 4)
            //     .flatMap { listOf(it, 100) }
            //     .map { it + 10 }

            // when
            val result = option {
                val foo = getFoo().bind()
                val bar = foo.bar.bind()
                val result = divideBy100(bar.number).bind()

                println("here")

                result + 100
            }

            // then
            result shouldBeSome 102
        }

        @Test
        fun filterOption() {
            // given
            val optionList = listOf(Some("foo"), None, Some("bar"))

            // when
            val result = optionList.filterOption()

            // then
            result shouldBe listOf("foo", "bar")
        }
    }
}
