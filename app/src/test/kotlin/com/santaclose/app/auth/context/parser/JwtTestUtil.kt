package com.santaclose.app.auth.context.parser

import com.santaclose.app.auth.context.AppSession
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.*

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
                    Date().apply { time += 1000 * 60L * 60L * 2L * (if (isExpired) -1 else 1) }
                )
                .setIssuedAt(Date())
                .signWith(key)
                .compact()
    }
}
