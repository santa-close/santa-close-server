package com.santaclose.app.mountainReview.controller

import arrow.core.Either.Companion.catch
import com.santaclose.app.auth.security.id
import com.santaclose.app.mountainReview.controller.dto.CreateMountainReviewAppInput
import com.santaclose.app.mountainReview.service.MountainReviewAppMutationService
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.exception.getOrThrow
import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Controller
class MountainReviewAppController(
    private val mountainAppMutationService: MountainReviewAppMutationService,
) {
    val logger = logger()

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    fun createMountainReview(
        @Valid @Argument
        input: CreateMountainReviewAppInput,
        authentication: Authentication,
    ): Mono<Boolean> =
        catch {
            println(input)
            mountainAppMutationService.register(input, authentication.id)
            true.toMono()
        }
            .onLeft { logger.error(it.message, it) }
            .getOrThrow()
}
