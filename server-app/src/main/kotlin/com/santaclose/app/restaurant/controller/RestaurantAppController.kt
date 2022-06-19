package com.santaclose.app.restaurant.controller

import arrow.core.Either.Companion.catch
import com.santaclose.app.auth.security.id
import com.santaclose.app.restaurant.controller.dto.CreateRestaurantAppInput
import com.santaclose.app.restaurant.controller.dto.RestaurantAppDetail
import com.santaclose.app.restaurant.service.RestaurantAppMutationService
import com.santaclose.app.restaurant.service.RestaurantAppQueryService
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.getOrThrow
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import javax.validation.Valid

@Controller
class RestaurantAppController(
    private val restaurantAppMutationService: RestaurantAppMutationService,
    private val restaurantAppQueryService: RestaurantAppQueryService,
) {
    val logger = logger()

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    fun restaurantDetail(@Argument id: String): RestaurantAppDetail =
        catch { restaurantAppQueryService.findDetail(id.toLong()) }
            .tapLeft { logger.error(it.message, it) }
            .getOrThrow()

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    fun createRestaurant(
        @Argument @Valid input: CreateRestaurantAppInput,
        authentication: Authentication,
    ): Boolean =
        catch {
            restaurantAppMutationService.createRestaurant(input, authentication.id)
            true
        }
            .tapLeft { logger.error(it.message, it) }
            .getOrThrow()
}
