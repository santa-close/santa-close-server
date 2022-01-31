package com.santaclose.app.auth.directive

import arrow.core.Option
import arrow.core.handleErrorWith
import com.expediagroup.graphql.generator.directives.KotlinFieldDirectiveEnvironment
import com.expediagroup.graphql.generator.directives.KotlinSchemaDirectiveWiring
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.web.error.UnauthorizedException
import com.santaclose.lib.web.error.toGraphQLException
import graphql.GraphQLException
import graphql.schema.DataFetcher
import graphql.schema.GraphQLFieldDefinition

class AuthSchemaDirectiveWiring : KotlinSchemaDirectiveWiring {
    override fun onField(environment: KotlinFieldDirectiveEnvironment): GraphQLFieldDefinition {
        val role = environment.directive.getArgument("role").argumentValue.value.let {
            when (it) {
                is AppUserRole -> it
                else -> throw GraphQLException("invalid app user role: $it")
            }
        }
        val originalDataFetcher = environment.getDataFetcher()

        val authDataFetcher = DataFetcher { dfe ->
            dfe.graphQlContext.get<Option<AppUser>>("user")
                .filter { user -> user.hasRole(role) }
                .handleErrorWith { throw UnauthorizedException("접근 권한이 없습니다").toGraphQLException() }

            originalDataFetcher.get(dfe)
        }
        environment.setDataFetcher(authDataFetcher)

        return environment.element
    }
}
