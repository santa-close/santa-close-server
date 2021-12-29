package com.santaclose.api.sample.resolver

import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Component

@Component
class SampleApiQueryResolver : Query {
    fun foo(): String = "bar"
}
