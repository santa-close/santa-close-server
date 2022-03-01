package com.santaclose.app.auth.resolver.dto

import arrow.core.Either
import arrow.core.Either.Companion.catch
import com.santaclose.app.auth.context.AppSession
import io.jsonwebtoken.Jwts
import java.security.Key
import java.time.LocalDateTime
import java.util.Date
import kotlin.time.Duration.Companion.days

data class AppAuthInfo(
    val accessToken: String,
    val expiredAt: LocalDateTime,
) {
    companion object {
        fun by(appSession: AppSession, key: Key, expiredDays: Long): Either<Throwable, AppAuthInfo> = catch {
            Jwts
                .builder()
                .setClaims(mapOf("id" to "${appSession.id}", "role" to appSession.role))
                .setExpiration(Date().apply { time += expiredDays.days.inWholeMilliseconds })
                .setIssuedAt(Date())
                .signWith(key)
                .compact()
                .let { AppAuthInfo(it, LocalDateTime.now().plusDays(expiredDays)) }
        }
    }
}
