package com.santaclose.app.util

import arrow.core.None
import arrow.core.toOption
import com.navercorp.fixturemonkey.kotlin.KFixtureMonkey
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.auth.context.AppGraphQLContextFactory
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.appUser.type.AppUserRole
import io.mockk.coEvery
import io.mockk.slot
import net.jqwik.api.Arbitraries
import org.springframework.web.reactive.function.server.ServerRequest

internal abstract class AppContextMocker {
    @MockkBean(relaxed = true)
    private lateinit var appGraphQLContextFactory: AppGraphQLContextFactory

    companion object {
        private val sut = KFixtureMonkey.create()
        private var idBuilder = Arbitraries.longs().between(0, 10000)
    }

    fun withMockUser(role: AppUserRole): AppUser {
        val appUser = sut.giveMeBuilder(AppUser::class.java)
            .set("role", role)
            .sample()
            .also { it.id = idBuilder.sample() }
        val slot = slot<ServerRequest>()

        coEvery { appGraphQLContextFactory.generateContextMap(capture(slot)) } answers {
            mapOf(
                "user" to appUser.toOption(),
                "request" to slot.captured
            )
        }

        return appUser
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
