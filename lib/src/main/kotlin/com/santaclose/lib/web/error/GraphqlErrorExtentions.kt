package com.santaclose.lib.web.error

import arrow.core.Either
import arrow.core.getOrHandle
import graphql.GraphqlErrorException
import javax.persistence.NoResultException

enum class GraphqlErrorCode {
    NOT_FOUND,
    SERVER_ERROR,
}

fun <A> Either<Throwable, A>.getOrThrow(): A =
    this.getOrHandle { throw it.toGraphQLError() }

fun Throwable.toGraphQLError(): Throwable = GraphqlErrorException.newErrorException()
    .cause(this)
    .message(this.message)
    .extensions(
        mutableMapOf(
            "code" to when (this) {
                is NoResultException -> GraphqlErrorCode.NOT_FOUND
                else -> GraphqlErrorCode.SERVER_ERROR
            }
        ) as Map<String, Any>?
    )
    .build()
