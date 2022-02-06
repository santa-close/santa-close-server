package com.santaclose.app.auth.context.parser

import arrow.core.None
import arrow.core.Option
import com.santaclose.app.auth.context.AppSession
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest

@Service
class ServerRequestParserImpl : ServerRequestParser {
    override fun parse(request: ServerRequest): Option<AppSession> = None
}
