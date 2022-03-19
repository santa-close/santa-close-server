package com.santaclose.app.mountainReview.resolver

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import com.santaclose.app.auth.context.userId
import com.santaclose.app.auth.directive.Auth
import com.santaclose.app.mountainReview.resolver.dto.CreateMountainReviewAppInput
import com.santaclose.app.mountainReview.service.MountainReviewAppMutationService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.toGraphQLException
import graphql.schema.DataFetchingEnvironment
import javax.validation.Valid
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

@Component
@Validated
class MountainReviewAppMutationResolver(
  private val mountainAppMutationService: MountainReviewAppMutationService,
) : Mutation {
  val logger = logger()

  @Auth(AppUserRole.USER)
  @GraphQLDescription("산 리뷰 등록하기")
  fun createMountainReview(
    @Valid input: CreateMountainReviewAppInput,
    dfe: DataFetchingEnvironment
  ): Boolean {
    try {
      mountainAppMutationService.register(input, dfe.userId())
      return true
    } catch (e: Throwable) {
      logger.error(e.message, e)
      throw e.toGraphQLException()
    }
  }
}
