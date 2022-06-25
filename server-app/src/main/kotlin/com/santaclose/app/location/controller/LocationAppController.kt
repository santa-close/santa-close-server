package com.santaclose.app.location.controller

import arrow.core.Either.Companion.catch
import com.santaclose.app.location.controller.dto.AppLocation
import com.santaclose.app.location.controller.dto.LocationAppInput
import com.santaclose.app.location.service.LocationAppQueryService
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.exception.getOrThrow
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import javax.validation.Valid

@Controller
class LocationAppController(
    private val locationAppQueryService: LocationAppQueryService,
) {
    private val logger = logger()

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    fun locations(@Argument @Valid input: LocationAppInput): Flux<AppLocation> =
        catch { locationAppQueryService.find(input).toFlux() }
            .tapLeft { logger.error(it.message, it) }
            .getOrThrow()
}
