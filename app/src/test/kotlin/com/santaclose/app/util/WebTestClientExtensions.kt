package com.santaclose.app.util

import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient

const val GRAPHQL_ENDPOINT = "/graphql"
val GRAPHQL_MEDIA_TYPE = MediaType("application", "graphql")
const val DATA_JSON_PATH = "$.data"
const val ERRORS_JSON_PATH = "$.errors"
const val EXTENSIONS_JSON_PATH = "$.extensions"

fun WebTestClient.query(query: String): WebTestClient.ResponseSpec =
    this.post()
        .uri(GRAPHQL_ENDPOINT)
        .accept(APPLICATION_JSON)
        .contentType(GRAPHQL_MEDIA_TYPE)
        .bodyValue(query)
        .exchange()
        .expectStatus().isOk

fun WebTestClient.mutation(mutation: String): WebTestClient.ResponseSpec = this.query(mutation)

fun WebTestClient.ResponseSpec.success(expectedQuery: String): WebTestClient.BodyContentSpec =
    this.expectBody()
        .jsonPath("$DATA_JSON_PATH.$expectedQuery").exists()
        .jsonPath(ERRORS_JSON_PATH).doesNotExist()
        .jsonPath(EXTENSIONS_JSON_PATH).doesNotExist()

fun WebTestClient.BodyContentSpec.verify(
    path: String,
    expectedData: Any
): WebTestClient.BodyContentSpec =
    this.jsonPath("$DATA_JSON_PATH.$path").isEqualTo(expectedData)

fun WebTestClient.ResponseSpec.verifyError(expectedError: String): WebTestClient.BodyContentSpec =
    this.expectBody()
        .jsonPath(DATA_JSON_PATH).doesNotExist()
        .jsonPath("$ERRORS_JSON_PATH.[0].message").isEqualTo(expectedError)
        .jsonPath(EXTENSIONS_JSON_PATH).doesNotExist()
