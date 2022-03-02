package com.santaclose.app.mountainReview.resolver

import com.expediagroup.graphql.server.operations.Mutation
import com.santaclose.app.mountainReview.resolver.dto.CreateMountainReviewAppInput
import com.santaclose.app.mountainReview.service.MountainReviewAppMutationService
import com.santaclose.lib.web.error.toGraphQLException
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Component
@Validated
class MountainReviewAppMutationResolver(
    private val mountainAppMutationService: MountainReviewAppMutationService,
) : Mutation {
    fun createMountainReview(@Valid input: CreateMountainReviewAppInput): Boolean {
        try {
            return mountainAppMutationService.register(input).run { true }
        } catch (e: Throwable) {
            throw e.toGraphQLException()
        }
    }
}
