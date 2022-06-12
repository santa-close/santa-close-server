package com.santaclose.app.location.resolver

import arrow.core.Either.Companion.catch
import com.santaclose.app.location.resolver.dto.AppLocation
import com.santaclose.app.location.resolver.dto.LocationAppInput
import com.santaclose.app.location.service.LocationAppQueryService
import com.santaclose.lib.web.error.getOrThrow
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

// @Component
// class LocationAppQueryResolver(
//    private val locationAppQueryService: LocationAppQueryService,
// ) : Query {
//    @Auth(AppUserRole.USER)
//    @GraphQLDescription("지도 위치 데이터")
//    fun locations(input: LocationAppInput): List<AppLocation> =
//        catch { locationAppQueryService.find(input) }.getOrThrow()
// }

@Controller
class LocationAppController(
    private val locationAppQueryService: LocationAppQueryService,
) {

    @QueryMapping
    fun location(@Argument input: LocationAppInput): List<AppLocation> =
        catch { locationAppQueryService.find(input) }.getOrThrow()
}
