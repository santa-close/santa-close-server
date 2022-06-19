package com.santaclose.app.restaurantReview.controller

import arrow.core.Either.Companion.catch
import com.santaclose.app.auth.security.id
import com.santaclose.app.restaurantReview.controller.dto.CreateRestaurantReviewAppInput
import com.santaclose.app.restaurantReview.service.RestaurantReviewAppMutationService
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.getOrThrow
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import javax.validation.Valid

@Controller
class RestaurantReviewAppController(
    private val restaurantReviewAppMutationService: RestaurantReviewAppMutationService,
) {
    private val logger = logger()

    @MutationMapping
    fun createRestaurantReview(
        @Argument @Valid input: CreateRestaurantReviewAppInput,
        authentication: Authentication,
    ): Boolean =
        catch {
            restaurantReviewAppMutationService.register(input, authentication.id)
            true
        }
            .tapLeft { logger.error(it.message, it) }
            .getOrThrow()
}
