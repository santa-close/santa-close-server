package com.santaclose.app.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

val beans = beans {
    bean { jacksonObjectMapper() }

    bean { corsWebFilter() }

    bean { graphQlSourceBuilderCustomizer }

    bean { kakaoApiService }

    bean { kakaoAuthService }
}

class BeansInitializer : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(context: GenericApplicationContext) {
        beans.initialize(context)
    }
}
