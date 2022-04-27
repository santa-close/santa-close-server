package com.santaclose.app.location.resolver

import arrow.core.Either.Companion.catch
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import com.santaclose.app.auth.directive.Auth
import com.santaclose.app.location.resolver.dto.AppLocation
import com.santaclose.app.location.resolver.dto.LocationAppInput
import com.santaclose.app.location.service.LocationAppQueryService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.web.error.getOrThrow
import org.springframework.stereotype.Component

@Component
class LocationAppQueryResolver(
  private val locationAppQueryService: LocationAppQueryService,
) : Query {
  @Auth(AppUserRole.USER)
  @GraphQLDescription("지도 위치 데이터")
  fun locations(input: LocationAppInput): List<AppLocation> =
    catch { locationAppQueryService.find(input) }.getOrThrow()
}
