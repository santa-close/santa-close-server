package com.santaclose.app.mountain.controller

import com.santaclose.app.auth.security.UserAuthorize
import com.santaclose.app.auth.security.id
import com.santaclose.app.mountain.controller.dto.CreateMountainAppInput
import com.santaclose.app.mountain.controller.dto.MountainAppDetail
import com.santaclose.app.mountain.controller.dto.MountainAppSummary
import com.santaclose.app.mountain.service.MountainAppMutationService
import com.santaclose.app.mountain.service.MountainAppQueryService
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
class MountainAppController(
    private val mountainAppQueryService: MountainAppQueryService,
    private val mountainAppMutationService: MountainAppMutationService,
) {
    val logger = logger()

    @QueryMapping
    @UserAuthorize
    fun mountainDetail(@Argument id: String): Mono<MountainAppDetail> =
        mountainAppQueryService
            .findDetail(id)
            .monoOrLog(logger)

    @QueryMapping
    @UserAuthorize
    fun mountainSummary(@Argument id: String): Mono<MountainAppSummary> =
        mountainAppQueryService
            .findOneSummary(id.toLong())
            .map { MountainAppSummary.by(it) }
            .monoOrLog(logger)

    @MutationMapping
    @UserAuthorize
    fun registerMountain(
        @Argument @Valid
        input: CreateMountainAppInput,
        authentication: Authentication,
    ): Mono<Boolean> =
        mountainAppMutationService
            .register(input, authentication.id)
            .map { true }
            .monoOrLog(logger)
}
