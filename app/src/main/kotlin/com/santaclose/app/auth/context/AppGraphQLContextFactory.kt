package com.santaclose.app.auth.context

import com.expediagroup.graphql.server.spring.execution.SpringGraphQLContext
import com.expediagroup.graphql.server.spring.execution.SpringGraphQLContextFactory
import com.santaclose.app.auth.context.parser.ServerRequestParser
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class AppGraphQLContextFactory(
  private val serverRequestParser: ServerRequestParser,
) : SpringGraphQLContextFactory<SpringGraphQLContext>() {
  override suspend fun generateContextMap(request: ServerRequest): Map<*, Any>? =
    mapOf(
      "session" to serverRequestParser.parse(request),
      "request" to request,
    )

  override suspend fun generateContext(request: ServerRequest): SpringGraphQLContext? = null
}
