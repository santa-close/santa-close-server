package com.santaclose.app.sample.resolver

import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Component

@Component
class SampleAppQueryResolver : Query {
    fun foo(): String = "bar"
}
