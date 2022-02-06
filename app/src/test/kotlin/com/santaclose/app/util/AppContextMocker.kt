package com.santaclose.app.util

import arrow.core.None
import arrow.core.toOption
import com.navercorp.fixturemonkey.kotlin.KFixtureMonkey
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.auth.context.AppGraphQLContextFactory
import com.santaclose.app.auth.context.AppSession
import com.santaclose.lib.entity.appUser.type.AppUserRole
import io.mockk.coEvery
import io.mockk.slot
import org.springframework.web.reactive.function.server.ServerRequest

internal abstract class AppContextMocker {
    @MockkBean(relaxed = true)
    private lateinit var appGraphQLContextFactory: AppGraphQLContextFactory

    companion object {
        private val sut = KFixtureMonkey.create()
    }

    fun withMockUser(role: AppUserRole): AppSession {
        val session = sut.giveMeBuilder(AppSession::class.java)
            .set("role", role)
            .sample()
        val slot = slot<ServerRequest>()

        coEvery { appGraphQLContextFactory.generateContextMap(capture(slot)) } answers {
            mapOf(
                "user" to session.toOption(),
                "request" to slot.captured
            )
        }

        return session
    }

    fun withAnonymousUser() {
        val slot = slot<ServerRequest>()
        coEvery { appGraphQLContextFactory.generateContextMap(capture(slot)) } answers {
            mapOf(
                "user" to None,
                "request" to slot.captured
            )
        }
    }
}
