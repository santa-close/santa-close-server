package com.santaclose.app.auth.context.parser

import com.santaclose.app.auth.context.AppSession
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.Date
import javax.crypto.SecretKey

internal class JwtTestUtil {
    companion object {
        private val key = Keys.hmacShaKeyFor("PdSgVkYp2s5v8y/B?E(H+MbQeThWmZq4".toByteArray())

        fun genKey(): SecretKey = Keys.secretKeyFor(SignatureAlgorithm.ES256)

        fun genToken(session: AppSession? = null): String =
            Jwts
                .builder()
                .apply {
                    session?.let { setClaims(mapOf("id" to "${session.id}", "role" to session.role)) }
                }
                .setExpiration(Date().apply { time += 1000 * 60L * 60L * 2L })
                .setIssuedAt(Date())
                .signWith(key)
                .compact()
    }
}
