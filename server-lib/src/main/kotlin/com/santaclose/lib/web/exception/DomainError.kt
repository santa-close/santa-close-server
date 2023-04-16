package com.santaclose.lib.web.exception

sealed class DomainError(message: String?, cause: Throwable?) : Error(message, cause) {
    data class NotFound(override val message: String) : DomainError(message, null)
    data class DBFailure(val error: Throwable) : DomainError(error.message, error)
}
