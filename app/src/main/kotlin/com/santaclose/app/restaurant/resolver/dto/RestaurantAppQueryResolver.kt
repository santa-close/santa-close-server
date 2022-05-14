package com.santaclose.app.restaurant.resolver.dto

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import com.santaclose.app.auth.directive.Auth
import com.santaclose.app.restaurant.service.RestaurantAppQueryService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.toGraphQLException
import com.santaclose.lib.web.toLong
import javax.validation.Valid
import org.springframework.stereotype.Component

@Component
class RestaurantAppQueryResolver(private val restaurantAppQueryService: RestaurantAppQueryService) :
  Query {
  val logger = logger()

  @Auth(AppUserRole.USER)
  @GraphQLDescription("맛집 상세 조회")
  fun restaurantDetail(
    @Valid input: RestaurantDetailAppInput,
  ): RestaurantAppDetail {
    try {
      return restaurantAppQueryService.findDetail(input.id.toLong())
    } catch (e: Throwable) {
      logger.error(e.message, e)
      throw e.toGraphQLException()
    }
  }
}
