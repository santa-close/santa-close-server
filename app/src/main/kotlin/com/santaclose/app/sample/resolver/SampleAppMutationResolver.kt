package com.santaclose.app.sample.resolver

import com.expediagroup.graphql.server.operations.Mutation
import com.santaclose.app.sample.resolver.dto.CreateSampleAppInput
import com.santaclose.app.sample.service.SampleAppMutationService
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Component
@Validated
class SampleAppMutationResolver(
    private val sample: SampleAppMutationService,
) : Mutation {
    fun createSample(@Valid input: CreateSampleAppInput): Boolean =
        sample.create(input.toEntity()).run { true }
}
