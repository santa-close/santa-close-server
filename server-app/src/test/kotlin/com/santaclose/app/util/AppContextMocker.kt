package com.santaclose.app.util

import com.navercorp.fixturemonkey.kotlin.KFixtureMonkey
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.santaclose.app.auth.security.AppSession
import com.santaclose.lib.entity.appUser.type.AppUserRole

internal abstract class AppContextMocker {
//    @MockkBean(relaxed = true) private lateinit var appGraphQLContextFactory: AppGraphQLContextFactory

    companion object {
        private val sut = KFixtureMonkey.create()
    }

    fun withMockUser(role: AppUserRole): AppSession {
        val session = sut.giveMeBuilder<AppSession>().set("role", role).sample()
//        val slot = slot<ServerRequest>()

//        coEvery { appGraphQLContextFactory.generateContextMap(capture(slot)) } answers
//            {
//                mapOf("session" to session.toOption(), "request" to slot.captured)
//            }

        return session
    }

    fun withAnonymousUser() {
//        val slot = slot<ServerRequest>()
//        coEvery { appGraphQLContextFactory.generateContextMap(capture(slot)) } answers
//            {
//                mapOf("session" to None, "request" to slot.captured)
//            }
    }
}
