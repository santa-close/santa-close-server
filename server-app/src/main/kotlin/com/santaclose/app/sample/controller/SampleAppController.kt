package com.santaclose.app.sample.controller

import com.santaclose.app.sample.controller.dto.CreateSampleAppInput
import com.santaclose.app.sample.controller.dto.SampleAppDetail
import com.santaclose.app.sample.controller.dto.SampleAppItemInput
import com.santaclose.app.sample.service.SampleAppMutationService
import com.santaclose.app.sample.service.SampleAppQueryService
import com.santaclose.lib.web.error.getOrThrow
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import javax.validation.Valid

@Controller
class SampleAppController(
    private val sampleAppQueryService: SampleAppQueryService,
    private val sampleAppMutationService: SampleAppMutationService,
) {

    @QueryMapping
    fun sample(@Argument @Valid input: SampleAppItemInput): Mono<SampleAppDetail> =
        sampleAppQueryService
            .findByPrice(input.price)
            .map { it.toMono() }
            .getOrThrow()

    @MutationMapping
    fun createSample(@Argument @Valid input: CreateSampleAppInput): Mono<Boolean> =
        sampleAppMutationService
            .create(input.toEntity())
            .run { true.toMono() }
}
