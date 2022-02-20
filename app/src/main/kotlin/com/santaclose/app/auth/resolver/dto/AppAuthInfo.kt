package com.santaclose.app.auth.resolver.dto

import arrow.core.Either
import arrow.core.Either.Companion.catch
import com.santaclose.app.auth.context.AppSession
import io.jsonwebtoken.Jwts
import java.security.Key
import java.time.LocalDateTime
import java.util.Date

data class AppAuthInfo(
    val accessToken: String,
    val expiredAt: LocalDateTime,
) {
    companion object {
        fun by(appSession: AppSession, key: Key, expiredDays: Int): Either<Throwable, AppAuthInfo> = catch {
            Jwts
                .builder()
                .setClaims(mapOf("id" to "${appSession.id}", "role" to appSession.role))
                .setExpiration(Date().apply { time += 1000 * 60L * 60L * expiredDays })
                .setIssuedAt(Date())
                .signWith(key)
                .compact()
                .let { AppAuthInfo(it, LocalDateTime.now()) }
        }
    }
}
