package com.santaclose.app.restaurant.resolver.dto

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID
import com.expediagroup.graphql.server.operations.Query
import com.santaclose.app.auth.directive.Auth
import com.santaclose.app.restaurant.service.RestaurantAppQueryService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.toGraphQLException
import com.santaclose.lib.web.toLong
import org.springframework.format.annotation.NumberFormat
import org.springframework.stereotype.Component

@Component
class RestaurantAppQueryResolver(private val restaurantAppQueryService: RestaurantAppQueryService) :
    Query {
    val logger = logger()

    @Auth(AppUserRole.USER)
    @GraphQLDescription("맛집 상세 조회")
    fun restaurantDetail(
        @NumberFormat(style = NumberFormat.Style.NUMBER) id: ID,
    ): RestaurantAppDetail {
        try {
            return restaurantAppQueryService.findDetail(id.toLong())
        } catch (e: Throwable) {
            logger.error(e.message, e)
            throw e.toGraphQLException()
        }
    }
}
