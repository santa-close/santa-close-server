package com.santaclose.app.restaurant.resolver

import com.santaclose.app.auth.context.userId
import com.santaclose.app.restaurant.resolver.dto.CreateRestaurantAppInput
import com.santaclose.app.restaurant.service.RestaurantAppMutationService
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.toGraphQLException
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
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

@Controller
class RestaurantAppController(
    private val restaurantAppMutationService: RestaurantAppMutationService,
) {
    val logger = logger()

    @MutationMapping
    fun createRestaurant(
        @Argument input: CreateRestaurantAppInput,
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
