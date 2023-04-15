package com.santaclose.lib.web.exception

sealed class DomainError(cause: Throwable?) : Throwable(cause) {
    data class NotFound(override val message: String) : DomainError(null)
    data class DBFailure(val error: Throwable) : DomainError(error)
}
