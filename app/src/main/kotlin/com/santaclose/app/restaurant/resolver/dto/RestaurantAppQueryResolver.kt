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
import org.springframework.validation.annotation.Validated

@Component
@Validated
class RestaurantAppQueryResolver(private val restaurantAppQueryService: RestaurantAppQueryService) :
  Query {
  val logger = logger()

  @Auth(AppUserRole.USER)
  @GraphQLDescription("맛집 상세 조회")
  fun restaurantDetail(
    @Valid input: RestaurantDetailAppInput,
  ): Boolean {
    try {
      restaurantAppQueryService.findDetail(input.id.toLong())
      return true
      // @XXX
      // 아래의 코드 처럼 RestaurantAppDetail dto를 반환하도록 하면 AppApplicationTests > generateSchema() 실행시 에러
      // 발생
      // return restaurantAppQueryService.findDetail(input.id)
    } catch (e: Throwable) {
      logger.error(e.message, e)
      throw e.toGraphQLException()
    }
  }
}
