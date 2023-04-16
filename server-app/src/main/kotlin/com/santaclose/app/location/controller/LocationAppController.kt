package com.santaclose.app.location.controller

import com.santaclose.app.location.controller.dto.AppLocation
import com.santaclose.app.location.controller.dto.LocationAppInput
import com.santaclose.app.location.service.LocationAppQueryService
import com.santaclose.lib.logger.logger
import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class LocationAppController(
    private val locationAppQueryService: LocationAppQueryService,
) {
    private val logger = logger()

    @QueryMapping
    suspend fun locations(
        @Argument @Valid
        input: LocationAppInput,
    ): List<AppLocation> =
        locationAppQueryService.find(input)
            .fold(
                { logger.error(it.message, it); throw it },
                { it },
            )
}
