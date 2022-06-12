package com.santaclose.app.restaurantReview.resolver

import com.santaclose.app.restaurantReview.resolver.dto.CreateRestaurantReviewAppInput
import com.santaclose.app.restaurantReview.service.RestaurantReviewAppMutationService
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.toGraphQLException
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

// @Component
// @Validated
// class RestaurantReviewAppMutationResolver(
//    private val restaurantReviewAppMutationService: RestaurantReviewAppMutationService
// ) : Mutation {
//    private val logger = logger()
//
//    @Auth(AppUserRole.USER)
//    @GraphQLDescription("음식점 리뷰 등록하기")
//    fun createRestaurantReview(
//        @Valid input: CreateRestaurantReviewAppInput,
//        dfe: DataFetchingEnvironment
//    ): Boolean {
//        try {
//            restaurantReviewAppMutationService.register(input, dfe.userId())
//            return true
//        } catch (e: Throwable) {
//            logger.error(e.message, e)
//            throw e.toGraphQLException()
//        }
//    }
// }

@Controller
class RestaurantReviewAppController(
    private val restaurantReviewAppMutationService: RestaurantReviewAppMutationService
) {
    private val logger = logger()

    @MutationMapping
    fun createRestaurantReview(
        @Argument input: CreateRestaurantReviewAppInput,
        // TODO: 인증 대응
        dfe: DataFetchingEnvironment
    ): Boolean {
        try {
            restaurantReviewAppMutationService.register(input, 123)
            return true
        } catch (e: Throwable) {
            logger.error(e.message, e)
            throw e.toGraphQLException()
        }
    }
}
