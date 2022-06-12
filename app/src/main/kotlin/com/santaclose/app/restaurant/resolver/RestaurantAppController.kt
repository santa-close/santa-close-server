package com.santaclose.app.restaurant.resolver

import com.santaclose.app.restaurant.resolver.dto.CreateRestaurantAppInput
import com.santaclose.app.restaurant.resolver.dto.RestaurantAppDetail
import com.santaclose.app.restaurant.service.RestaurantAppMutationService
import com.santaclose.app.restaurant.service.RestaurantAppQueryService
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.toGraphQLException
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

// @Component
// @Validated
// class RestaurantAppMutationResolver(
//    private val restaurantAppMutationService: RestaurantAppMutationService,
// ) : Mutation {
//    val logger = logger()
//
//    @Auth(AppUserRole.USER)
//    @GraphQLDescription("맛집 등록하기")
//    fun createRestaurant(
//        @Valid input: CreateRestaurantAppInput,
//        dfe: DataFetchingEnvironment
//    ): Boolean {
//        try {
//            restaurantAppMutationService.createRestaurant(input, dfe.userId())
//            return true
//        } catch (e: Throwable) {
//            logger.error(e.message, e)
//            throw e.toGraphQLException()
//        }
//    }
// }

// @Component
// class RestaurantAppQueryResolver(
//    private val restaurantAppQueryService: RestaurantAppQueryService
// ) : Query {
//    val logger = logger()
//
//    @Auth(AppUserRole.USER)
//    @GraphQLDescription("맛집 상세 조회")
//    fun restaurantDetail(
//        @NumberFormat(style = NumberFormat.Style.NUMBER) id: ID,
//    ): RestaurantAppDetail {
//        try {
//            return restaurantAppQueryService.findDetail(id.toLong())
//        } catch (e: Throwable) {
//            logger.error(e.message, e)
//            throw e.toGraphQLException()
//        }
//    }
// }

@Controller
class RestaurantAppController(
    private val restaurantAppMutationService: RestaurantAppMutationService,
    private val restaurantAppQueryService: RestaurantAppQueryService,
) {
    val logger = logger()

    @QueryMapping
    fun restaurantDetail(@Argument id: String): RestaurantAppDetail {
        try {
            return restaurantAppQueryService.findDetail(id.toLong())
        } catch (e: Throwable) {
            logger.error(e.message, e)
            throw e.toGraphQLException()
        }
    }

    @MutationMapping
    fun createRestaurant(
        @Argument input: CreateRestaurantAppInput,
        // TODO: 인증 대응
        dfe: DataFetchingEnvironment
    ): Boolean {
        try {
            restaurantAppMutationService.createRestaurant(input, 123)
            return true
        } catch (e: Throwable) {
            logger.error(e.message, e)
            throw e.toGraphQLException()
        }
    }
}
