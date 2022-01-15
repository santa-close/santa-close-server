package com.santaclose.app.sample.resolver

import arrow.core.getOrHandle
import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.server.operations.Query
import com.santaclose.app.sample.resolver.dto.CategoryDto
import com.santaclose.app.sample.resolver.dto.SampleDto
import com.santaclose.app.sample.resolver.dto.SampleInput
import com.santaclose.app.sample.service.SampleAppQueryService
import org.springframework.stereotype.Component

@Component
class SampleAppQueryResolver(
    private val sampleAppQueryService: SampleAppQueryService,
) : Query {
    fun sample(input: SampleInput): SampleDto =
        sampleAppQueryService
            .findByPrice(input.price)
            .getOrHandle {
                throw GraphQLKotlinException("가져오기 실패", it)
            }

    fun categories() = CategoryDto()
}
