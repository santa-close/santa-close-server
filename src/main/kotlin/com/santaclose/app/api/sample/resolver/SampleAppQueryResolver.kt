package com.santaclose.app.api.sample.resolver

import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Component

@Component
class SampleAppQueryResolver: Query {

    fun foo(): String {
        return "bar"
    }
}