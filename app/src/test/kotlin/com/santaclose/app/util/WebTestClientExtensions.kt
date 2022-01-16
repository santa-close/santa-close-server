package com.santaclose.app.util

import com.santaclose.lib.web.error.GraphqlErrorCode
import org.intellij.lang.annotations.Language
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient

const val GRAPHQL_ENDPOINT = "/graphql"
val GRAPHQL_MEDIA_TYPE = MediaType("application", "graphql")
const val DATA_JSON_PATH = "$.data"
const val ERRORS_JSON_PATH = "$.errors"
const val EXTENSIONS_JSON_PATH = "$.extensions"

inline class QueryInput(@Language("GraphQL") val query: String)

fun WebTestClient.query(queryInput: QueryInput): WebTestClient.ResponseSpec =
    this.post()
        .uri(GRAPHQL_ENDPOINT)
        .accept(APPLICATION_JSON)
        .contentType(GRAPHQL_MEDIA_TYPE)
        .bodyValue(queryInput.query)
        .exchange()
        .expectStatus().isOk

fun WebTestClient.ResponseSpec.success(query: String): WebTestClient.BodyContentSpec =
    this.expectBody()
        .jsonPath("$DATA_JSON_PATH.$query").exists()
        .jsonPath(ERRORS_JSON_PATH).doesNotExist()
        .jsonPath(EXTENSIONS_JSON_PATH).doesNotExist()

fun WebTestClient.BodyContentSpec.verify(path: String, data: Any): WebTestClient.BodyContentSpec =
    this.jsonPath("$DATA_JSON_PATH.$path").isEqualTo(data)

fun WebTestClient.ResponseSpec.verifyError(code: GraphqlErrorCode, message: String): WebTestClient.BodyContentSpec =
    this.expectBody()
        .jsonPath(DATA_JSON_PATH).doesNotExist()
        .jsonPath("$ERRORS_JSON_PATH.[0].extensions.code").isEqualTo(code.name)
        .jsonPath("$ERRORS_JSON_PATH.[0].message").isEqualTo(message)
        .jsonPath(EXTENSIONS_JSON_PATH).doesNotExist()
