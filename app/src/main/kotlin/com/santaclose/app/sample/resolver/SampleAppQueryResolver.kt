package com.santaclose.app.sample.resolver

import arrow.core.getOrHandle
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.server.operations.Query
import com.santaclose.app.sample.resolver.dto.SampleAppDetail
import com.santaclose.app.sample.resolver.dto.SampleAppItemInput
import com.santaclose.app.sample.service.SampleAppQueryService
import org.springframework.stereotype.Component

@Component
class SampleAppQueryResolver(
    private val sampleAppQueryService: SampleAppQueryService,
) : Query {
    @GraphQLDescription("샘플 데이터")
    fun sample(input: SampleAppItemInput): SampleAppDetail =
        sampleAppQueryService
            .findByPrice(input.price)
            .getOrHandle {
                throw GraphQLKotlinException("가져오기 실패", it)
            }
}
