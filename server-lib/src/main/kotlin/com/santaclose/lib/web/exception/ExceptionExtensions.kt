package com.santaclose.lib.web.exception

import arrow.core.Either
import arrow.core.getOrHandle

fun <A> Either<Throwable, A>.getOrThrow(): A = this.getOrHandle { throw it }
