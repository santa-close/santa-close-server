package com.santaclose.app.auth.context.parser

import arrow.core.Option
import com.santaclose.app.auth.context.AppSession
import org.springframework.http.server.reactive.ServerHttpRequest

interface ServerRequestParser {
    fun parse(request: ServerHttpRequest): Option<AppSession>
    fun parseJwt(token: String): Option<AppSession>
}
