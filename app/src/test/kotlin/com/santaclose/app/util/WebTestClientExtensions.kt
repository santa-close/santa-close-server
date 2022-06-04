package com.santaclose.app.util

import com.santaclose.lib.web.error.GraphqlErrorCode
import io.kotest.matchers.string.shouldContain
import org.intellij.lang.annotations.Language
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.JsonPathAssertions
import org.springframework.test.web.reactive.server.WebTestClient

const val GRAPHQL_ENDPOINT = "/graphql"
val GRAPHQL_MEDIA_TYPE = MediaType("application", "graphql")
const val DATA_JSON_PATH = "$.data"
const val ERRORS_JSON_PATH = "$.errors"
const val EXTENSIONS_JSON_PATH = "$.extensions"

@JvmInline value class GraphqlBody(@Language("GraphQL") val body: String)

fun WebTestClient.gqlRequest(queryInput: GraphqlBody): WebTestClient.ResponseSpec =
    this.post()
        .uri(GRAPHQL_ENDPOINT)
        .accept(APPLICATION_JSON)
        .contentType(GRAPHQL_MEDIA_TYPE)
        .bodyValue(queryInput.body)
        .exchange()
        .expectStatus()
        .isOk

fun WebTestClient.ResponseSpec.withSuccess(
    @Language("JSONPath") query: String,
    scope: BodySpec.() -> Unit = {}
) {
    this.expectBody()
        .jsonPath("$DATA_JSON_PATH.$query")
        .exists()
        .jsonPath(ERRORS_JSON_PATH)
        .doesNotExist()
        .jsonPath(EXTENSIONS_JSON_PATH)
        .doesNotExist()
        .let { BodySpec(query, it) }
        .apply(scope)
}

fun WebTestClient.ResponseSpec.withError(
    code: GraphqlErrorCode,
    message: String? = null
): WebTestClient.BodyContentSpec =
    this.expectBody()
        .jsonPath(DATA_JSON_PATH)
        .doesNotExist()
        .jsonPath("$ERRORS_JSON_PATH.[0].extensions.code")
        .isEqualTo(code.name)
        .apply {
            message?.let {
                jsonPath("$ERRORS_JSON_PATH.[0].message")
                    .value({ it shouldContain message }, String::class.java)
            }
        }
        .jsonPath(EXTENSIONS_JSON_PATH)
        .doesNotExist()

class BodySpec(
    val query: String,
    val spec: WebTestClient.BodyContentSpec,
) {
    fun expect(@Language("JSONPath") path: String, withDot: Boolean = true): JsonPathAssertions =
        spec.jsonPath("$DATA_JSON_PATH.$query${if (withDot) "." else ""}$path")

    inline fun <reified T> parse(@Language("JSONPath") path: String, withDot: Boolean = true): T {
        var result: T? = null
        spec
            .jsonPath("$DATA_JSON_PATH.$query${if (withDot) "." else ""}$path")
            .value({ result = it }, T::class.java)

        return result ?: throw Exception("failed to parse path: $path")
    }

    inline fun <reified T> parse(): T {
        var result: T? = null
        spec.jsonPath("$DATA_JSON_PATH.$query").value({ result = it }, T::class.java)

        return result ?: throw Exception("failed to parse path: $query")
    }
}
