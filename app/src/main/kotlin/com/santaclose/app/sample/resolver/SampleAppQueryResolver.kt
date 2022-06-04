package com.santaclose.app.sample.resolver

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import com.santaclose.app.auth.directive.Auth
import com.santaclose.app.sample.resolver.dto.SampleAppDetail
import com.santaclose.app.sample.resolver.dto.SampleAppItemInput
import com.santaclose.app.sample.service.SampleAppQueryService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.web.error.getOrThrow
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Component
@Validated
class SampleAppQueryResolver(
    private val sampleAppQueryService: SampleAppQueryService,
) : Query {
    @Auth(AppUserRole.USER)
    @GraphQLDescription("샘플 데이터")
    fun sample(@Valid input: SampleAppItemInput): SampleAppDetail =
        sampleAppQueryService.findByPrice(input.price).getOrThrow()
}
