package com.santaclose.app.sample.resolver

import com.santaclose.app.sample.resolver.dto.CreateSampleAppInput
import com.santaclose.app.sample.resolver.dto.SampleAppDetail
import com.santaclose.app.sample.resolver.dto.SampleAppItemInput
import com.santaclose.app.sample.service.SampleAppMutationService
import com.santaclose.app.sample.service.SampleAppQueryService
import com.santaclose.lib.web.error.getOrThrow
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

// @Component
// @Validated
// class SampleAppQueryResolver(
//    private val sampleAppQueryService: SampleAppQueryService,
// ) : Query {
//    @Auth(AppUserRole.USER)
//    @GraphQLDescription("샘플 데이터")
//    fun sample(@Valid input: SampleAppItemInput): SampleAppDetail =
//        sampleAppQueryService.findByPrice(input.price).getOrThrow()
// }

@Controller
class SampleAppController(
    private val sampleAppQueryService: SampleAppQueryService,
    private val sampleAppMutationService: SampleAppMutationService,
) {

    @QueryMapping
    fun sample(@Argument input: SampleAppItemInput): SampleAppDetail =
        sampleAppQueryService.findByPrice(input.price).getOrThrow()

    @MutationMapping
    fun createSample(@Argument input: CreateSampleAppInput): Boolean =
        sampleAppMutationService.create(input.toEntity()).run { true }
}
