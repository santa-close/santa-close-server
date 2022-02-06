package com.santaclose.app.auth.context.parser

import arrow.core.Option
import arrow.core.Option.Companion.catch
import arrow.core.firstOrNone
import arrow.core.lastOrNone
import com.santaclose.app.auth.context.AppSession
import com.santaclose.app.config.JWTConfig
import com.santaclose.lib.entity.appUser.type.AppUserRole
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest

@Service
class ServerRequestParserImpl(jwtConfig: JWTConfig) : ServerRequestParser {
    private val parserBuilder = Jwts.parserBuilder()
    private val key = Keys.hmacShaKeyFor(jwtConfig.secret.toByteArray())

    override fun parse(request: ServerRequest): Option<AppSession> =
        request
            .headers()
            .header("Authorization")
            .firstOrNone()
            .flatMap { it.split(" ").lastOrNone() }
            .flatMap(::parseJwt)

    private fun parseJwt(token: String): Option<AppSession> = catch {
        parserBuilder
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .let {
                AppSession(
                    (it["id"] as String).toLong(),
                    AppUserRole.valueOf(it["role"] as String)
                )
            }
    }
}
