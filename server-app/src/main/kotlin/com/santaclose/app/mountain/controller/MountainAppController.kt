package com.santaclose.app.mountain.controller

import arrow.core.Either.Companion.catch
import com.santaclose.app.auth.security.id
import com.santaclose.app.mountain.controller.dto.CreateMountainAppInput
import com.santaclose.app.mountain.controller.dto.MountainAppSummary
import com.santaclose.app.mountain.controller.dto.MountainDetailAppInput
import com.santaclose.app.mountain.service.MountainAppMutationService
import com.santaclose.app.mountain.service.MountainAppQueryService
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
class MountainAppController(
    private val mountainAppQueryService: MountainAppQueryService,
    private val mountainAppMutationService: MountainAppMutationService,
) {
    val logger = logger()

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    fun mountainDetail(
        @Argument @Valid input: MountainDetailAppInput,
    ): Boolean =
        catch {
            mountainAppQueryService.findDetail(input.id)
            true
        }
            .tapLeft { logger.error(it.message, it) }
            .getOrThrow()

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    fun mountainSummary(@Argument id: String): MountainAppSummary =
        catch { mountainAppQueryService.findOneSummary(id.toLong()).let(MountainAppSummary::by) }
            .tapLeft { logger.error(it.message, it) }
            .getOrThrow()

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    fun registerMountain(
        @Argument @Valid input: CreateMountainAppInput,
        authentication: Authentication,
    ): Boolean =
        catch {
            mountainAppMutationService.register(input, authentication.id)
            true
        }
            .tapLeft { logger.error(it.message, it) }
            .getOrThrow()
}
