package com.santaclose.app.auth.context

import arrow.core.Option
import arrow.core.getOrElse
import com.santaclose.lib.web.error.toGraphQLException
import graphql.schema.DataFetchingEnvironment

fun DataFetchingEnvironment.session(): Option<AppSession> =
    this.graphQlContext.get("session")

fun DataFetchingEnvironment.user(): AppSession =
    this.session()
        .getOrElse { throw Exception("사용자가 존재하지 않습니다").toGraphQLException() }

fun DataFetchingEnvironment.userId(): Long =
    this.user().id
