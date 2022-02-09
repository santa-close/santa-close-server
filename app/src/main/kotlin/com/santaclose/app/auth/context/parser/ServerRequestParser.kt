package com.santaclose.app.auth.context.parser

import arrow.core.Option
import com.santaclose.app.auth.context.AppSession
import org.springframework.web.reactive.function.server.ServerRequest

interface ServerRequestParser {
    fun parse(request: ServerRequest): Option<AppSession>
}
