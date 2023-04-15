package com.santaclose.lib.web.exception // ktlint-disable filename
import arrow.core.Either
import arrow.core.getOrElse
import org.slf4j.Logger
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

fun <A> Either<Throwable, A>.getOrThrow(): A = this.getOrElse { throw it }

fun <A : Any> Either<Throwable, A>.monoOrLog(logger: Logger): Mono<A> = this.fold(
    { logger.error(it.message, it); throw it },
    { it.toMono() },
)

fun <T> Either.Companion.catchDB(f: () -> T): Either<DomainError.DBFailure, T> = try {
    Either.Right(f())
} catch (e: Throwable) {
    Either.Left(DomainError.DBFailure(e))
}
