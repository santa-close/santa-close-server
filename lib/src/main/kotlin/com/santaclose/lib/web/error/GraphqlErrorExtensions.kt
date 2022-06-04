package com.santaclose.lib.web.error

import arrow.core.Either
import arrow.core.getOrHandle
import graphql.GraphqlErrorException
import javax.persistence.NoResultException

enum class GraphqlErrorCode {
    NOT_FOUND,
    SERVER_ERROR,
    UNAUTHORIZED,
    BAD_REQUEST
}

fun <A> Either<Throwable, A>.getOrThrow(): A = this.getOrHandle { throw it.toGraphQLException() }

fun Throwable.toGraphQLException(): Throwable =
    GraphqlErrorException.newErrorException()
        .cause(this)
        .message(this.message)
        .extensions(
            mutableMapOf(
                "code" to
                    when (this) {
                        is NoResultException -> GraphqlErrorCode.NOT_FOUND
                        is NumberFormatException, is IllegalArgumentException -> GraphqlErrorCode.BAD_REQUEST
                        is HttpException -> code
                        else -> GraphqlErrorCode.SERVER_ERROR
                    }
            ) as
                Map<String, Any>?
        )
        .build()
