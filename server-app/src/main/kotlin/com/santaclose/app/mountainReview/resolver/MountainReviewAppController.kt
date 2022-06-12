package com.santaclose.app.mountainReview.resolver

import com.santaclose.app.mountainReview.resolver.dto.CreateMountainReviewAppInput
import com.santaclose.app.mountainReview.service.MountainReviewAppMutationService
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.toGraphQLException
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

// @Component
// @Validated
// class MountainReviewAppMutationResolver(
//    private val mountainAppMutationService: MountainReviewAppMutationService,
// ) : Mutation {
//    val logger = logger()
//
//    @Auth(AppUserRole.USER)
//    @GraphQLDescription("산 리뷰 등록하기")
//    fun createMountainReview(
//        @Valid input: CreateMountainReviewAppInput,
//        dfe: DataFetchingEnvironment
//    ): Boolean {
//        try {
//            mountainAppMutationService.register(input, dfe.userId())
//            return true
//        } catch (e: Throwable) {
//            logger.error(e.message, e)
//            throw e.toGraphQLException()
//        }
//    }
// }

@Controller
class MountainReviewAppController(
    private val mountainAppMutationService: MountainReviewAppMutationService,
) {
    val logger = logger()

    @MutationMapping
    fun createMountainReview(
        @Argument input: CreateMountainReviewAppInput,
        // TODO: 인증 처리
        dfe: DataFetchingEnvironment,
    ): Boolean {
        try {
            mountainAppMutationService.register(input, 123)
            return true
        } catch (e: Throwable) {
            logger.error(e.message, e)
            throw e.toGraphQLException()
        }
    }
}
