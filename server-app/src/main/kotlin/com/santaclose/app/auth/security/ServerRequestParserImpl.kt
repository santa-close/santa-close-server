package com.santaclose.app.auth.security

import arrow.core.Either.Companion.catch
import arrow.core.Option
import arrow.core.firstOrNone
import com.santaclose.app.config.JWTConfig
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.logger.logger
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service

@Service
class ServerRequestParserImpl(private val jwtConfig: JWTConfig) : ServerRequestParser {
    private val parserBuilder = Jwts.parserBuilder()
    private val logger = logger()

    override fun parse(request: ServerHttpRequest): Option<AppSession> =
        request
            .headers
            .getOrDefault(HttpHeaders.AUTHORIZATION, emptyList())
            .firstOrNone()
            .filter { it.startsWith("Bearer ") }
            .map { it.substring(7) }
            .flatMap(::parseJwt)

    private fun parseJwt(token: String): Option<AppSession> =
        catch {
            parserBuilder.setSigningKey(jwtConfig.key).build().parseClaimsJws(token).body.let {
                AppSession((it["id"] as String).toLong(), AppUserRole.valueOf(it["role"] as String))
            }
        }
            .mapLeft { logger.info("failed to parse token: token=$token message=${it.message}") }
            .orNone()
}
