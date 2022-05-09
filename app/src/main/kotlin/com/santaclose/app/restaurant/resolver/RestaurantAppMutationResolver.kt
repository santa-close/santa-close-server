package com.santaclose.app.restaurant.resolver

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import com.santaclose.app.auth.context.userId
import com.santaclose.app.auth.directive.Auth
import com.santaclose.app.restaurant.resolver.dto.CreateRestaurantAppInput
import com.santaclose.app.restaurant.service.RestaurantAppMutationService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.toGraphQLException
import graphql.schema.DataFetchingEnvironment
import javax.validation.Valid
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

@Component
@Validated
class RestaurantAppMutationResolver(
  private val restaurantAppMutationService: RestaurantAppMutationService,
) : Mutation {
  val logger = logger()

  @Auth(AppUserRole.USER)
  @GraphQLDescription("맛집 등록하기")
  fun createRestaurant(
    @Valid input: CreateRestaurantAppInput,
    dfe: DataFetchingEnvironment
  ): Boolean {
    try {
      restaurantAppMutationService.createRestaurant(input, dfe.userId())
      return true
    } catch (e: Throwable) {
      logger.error(e.message, e)
      throw e.toGraphQLException()
    }
  }
}
