package com.santaclose.lib.web.error

sealed class HttpException(message: String, val code: GraphqlErrorCode) : Throwable(message) {
    class UnauthorizedException(message: String) :
        HttpException(message, GraphqlErrorCode.UNAUTHORIZED)
}
