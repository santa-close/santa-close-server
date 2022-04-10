package com.santaclose.app.restaurantReview.resolver

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import com.santaclose.app.auth.context.userId
import com.santaclose.app.auth.directive.Auth
import com.santaclose.app.restaurantReview.resolver.dto.CreateRestaurantReviewAppInput
import com.santaclose.app.restaurantReview.service.RestaurantReviewAppMutationService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.toGraphQLException
import graphql.schema.DataFetchingEnvironment
import javax.validation.Valid
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

@Component
@Validated
class RestaurantReviewAppMutationResolver(
  private val restaurantReviewAppMutationService: RestaurantReviewAppMutationService
) : Mutation {
  private val logger = logger()

  @Auth(AppUserRole.USER)
  @GraphQLDescription("음식점 리뷰 등록하기")
  fun createRestaurantReview(
    @Valid input: CreateRestaurantReviewAppInput,
    dfe: DataFetchingEnvironment
  ): Boolean {
    try {
      restaurantReviewAppMutationService.register(input, dfe.userId())
      return true
    } catch (e: Throwable) {
      logger.error(e.message, e)
      throw e.toGraphQLException()
    }
  }
}
