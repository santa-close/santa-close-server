package com.santaclose.app.auth.context

import arrow.core.toOption
import com.expediagroup.graphql.server.spring.execution.SpringGraphQLContext
import com.expediagroup.graphql.server.spring.execution.SpringGraphQLContextFactory
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.appUser.type.AppUserRole
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class AppGraphQLContextFactory : SpringGraphQLContextFactory<SpringGraphQLContext>() {
    override suspend fun generateContextMap(request: ServerRequest): Map<*, Any>? {
        return mapOf(
            "user" to AppUser("01011112222", "socialId", AppUserRole.USER).toOption(),
            "request" to request,
        )
    }

    override suspend fun generateContext(request: ServerRequest): SpringGraphQLContext? = null
}
