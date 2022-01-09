package com.santaclose.app.sample.resolver

import arrow.core.getOrElse
import com.expediagroup.graphql.server.operations.Query
import com.santaclose.app.sample.repository.SampleAppRepository
import com.santaclose.app.sample.resolver.dto.SampleDto
import com.santaclose.app.sample.resolver.dto.SampleInput
import com.santaclose.lib.entity.sample.type.SampleStatus
import org.springframework.stereotype.Component

@Component
class SampleAppQueryResolver(val repository: SampleAppRepository) : Query {
    fun sample(input: SampleInput): SampleDto {
        return repository
            .findByPrice(input.price)
            .getOrElse { SampleDto("", 0, SampleStatus.CLOSE) }
    }
}
