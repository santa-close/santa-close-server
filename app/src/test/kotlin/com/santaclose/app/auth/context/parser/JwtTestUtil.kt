package com.santaclose.app.auth.context.parser

import com.santaclose.app.auth.context.AppSession
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.Date
import kotlin.time.Duration.Companion.hours

internal class JwtTestUtil {
    companion object {
        const val secret = "PdSgVkYp2s5v8y/B?E(H+MbQeThWmZq4"

        private val key = Keys.hmacShaKeyFor(secret.toByteArray())

        fun genToken(session: AppSession? = null, isExpired: Boolean = false): String =
            Jwts
                .builder()
                .apply {
                    session?.let { setClaims(mapOf("id" to "${session.id}", "role" to session.role)) }
                }
                .setExpiration(
                    Date().apply { time += (if (isExpired) -1 else 1).hours.inWholeMilliseconds }
                )
                .setIssuedAt(Date())
                .signWith(key)
                .compact()
    }
}
