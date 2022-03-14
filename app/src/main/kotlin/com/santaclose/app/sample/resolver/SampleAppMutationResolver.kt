package com.santaclose.app.sample.resolver

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import com.santaclose.app.auth.directive.Auth
import com.santaclose.app.sample.resolver.dto.CreateSampleAppInput
import com.santaclose.app.sample.service.SampleAppMutationService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import javax.validation.Valid
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

@Component
@Validated
class SampleAppMutationResolver(
  private val sampleAppMutationService: SampleAppMutationService,
) : Mutation {
  @Auth(AppUserRole.USER)
  @GraphQLDescription("샘플 데이터")
  fun createSample(@Valid input: CreateSampleAppInput): Boolean =
    sampleAppMutationService.create(input.toEntity()).run { true }
}
