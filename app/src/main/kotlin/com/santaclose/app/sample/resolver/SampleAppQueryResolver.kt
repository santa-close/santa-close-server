package com.santaclose.app.sample.resolver

import arrow.core.getOrElse
import com.expediagroup.graphql.server.operations.Query
import com.santaclose.app.sample.resolver.dto.CategoryDto
import com.santaclose.app.sample.resolver.dto.SampleDto
import com.santaclose.app.sample.resolver.dto.SampleInput
import com.santaclose.app.sample.service.SampleAppQueryService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class SampleAppQueryResolver(private val sampleAppQueryService: SampleAppQueryService) : Query {
    fun sample(input: SampleInput): SampleDto {
        return sampleAppQueryService
            .findByPrice(input.price)
            .getOrElse {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "데이터가 없습니다")
            }
    }

    fun categories() = CategoryDto()
}
