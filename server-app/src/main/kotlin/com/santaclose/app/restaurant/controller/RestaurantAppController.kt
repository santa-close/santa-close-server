package com.santaclose.app.restaurant.controller

import com.santaclose.app.auth.security.UserAuthorize
import com.santaclose.app.auth.security.id
import com.santaclose.app.restaurant.controller.dto.CreateRestaurantAppInput
import com.santaclose.app.restaurant.controller.dto.RestaurantAppDetail
import com.santaclose.app.restaurant.controller.dto.RestaurantAppSummary
import com.santaclose.app.restaurant.service.RestaurantAppMutationService
import com.santaclose.app.restaurant.service.RestaurantAppQueryService
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.exception.monoOrLog
import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class RestaurantAppController(
    private val restaurantAppMutationService: RestaurantAppMutationService,
    private val restaurantAppQueryService: RestaurantAppQueryService,
) {
    val logger = logger()

    @QueryMapping
    @UserAuthorize
    fun restaurantDetail(@Argument id: String): Mono<RestaurantAppDetail> =
        restaurantAppQueryService
            .findDetail(id.toLong())
            .monoOrLog(logger)

    @MutationMapping
    @UserAuthorize
    fun createRestaurant(
        @Argument @Valid
        input: CreateRestaurantAppInput,
        authentication: Authentication,
    ): Mono<Boolean> =
        restaurantAppMutationService
            .createRestaurant(input, authentication.id)
            .map { true }
            .monoOrLog(logger)

    @QueryMapping
    @UserAuthorize
    fun restaurantSummary(@Argument id: String): Mono<RestaurantAppSummary> =
        restaurantAppQueryService
            .findOneSummary(id.toLong())
            .map(RestaurantAppSummary::by)
            .monoOrLog(logger)
}
