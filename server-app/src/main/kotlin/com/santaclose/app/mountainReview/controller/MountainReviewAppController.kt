package com.santaclose.app.mountainReview.controller

import com.santaclose.app.auth.security.id
import com.santaclose.app.mountainReview.controller.dto.CreateMountainReviewAppInput
import com.santaclose.app.mountainReview.service.MountainReviewAppMutationService
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.exception.monoOrLog
import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

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
        mountainAppMutationService
            .register(input, authentication.id)
            .map { true }
            .monoOrLog(logger)
}
