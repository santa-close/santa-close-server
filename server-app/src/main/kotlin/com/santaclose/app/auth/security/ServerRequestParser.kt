package com.santaclose.app.auth.security

import arrow.core.Option
import org.springframework.http.server.reactive.ServerHttpRequest

interface ServerRequestParser {
    fun parse(request: ServerHttpRequest): Option<AppSession>
}
