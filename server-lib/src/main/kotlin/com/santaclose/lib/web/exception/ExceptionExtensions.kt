package com.santaclose.lib.web.exception // ktlint-disable filename

import arrow.core.Either
import arrow.core.getOrElse

fun <A> Either<Throwable, A>.getOrThrow(): A = this.getOrElse { throw it }
