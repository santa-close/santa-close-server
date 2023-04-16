package com.santaclose.app.sample.controller

import com.santaclose.app.sample.controller.dto.CreateSampleAppInput
import com.santaclose.app.sample.controller.dto.SampleAppDetail
import com.santaclose.app.sample.controller.dto.SampleAppItemInput
import com.santaclose.app.sample.service.SampleAppMutationService
import com.santaclose.app.sample.service.SampleAppQueryService
import com.santaclose.lib.web.exception.getOrThrow
import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class SampleAppController(
    private val sampleAppQueryService: SampleAppQueryService,
    private val sampleAppMutationService: SampleAppMutationService,
) {

    @QueryMapping
    fun sample(
        @Argument @Valid
        input: SampleAppItemInput,
    ): SampleAppDetail =
        sampleAppQueryService
            .findByPrice(input.price)
            .getOrThrow()

    @MutationMapping
    fun createSample(
        @Argument @Valid
        input: CreateSampleAppInput,
    ): Boolean =
        sampleAppMutationService
            .create(input.toEntity())
            .map { true }
            .getOrThrow()
}
