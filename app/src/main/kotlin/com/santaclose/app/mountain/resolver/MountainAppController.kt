package com.santaclose.app.mountain.resolver

import arrow.core.Either.Companion.catch
import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.auth.context.userId
import com.santaclose.app.mountain.resolver.dto.CreateMountainAppInput
import com.santaclose.app.mountain.resolver.dto.MountainAppSummary
import com.santaclose.app.mountain.resolver.dto.MountainDetailAppInput
import com.santaclose.app.mountain.service.MountainAppMutationService
import com.santaclose.app.mountain.service.MountainAppQueryService
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.getOrThrow
import com.santaclose.lib.web.error.toGraphQLException
import com.santaclose.lib.web.toLong
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import javax.validation.Valid

// @Component
// @Validated
// class MountainAppQueryResolver(
//    private val mountainAppQueryService: MountainAppQueryService,
// ) : Query {
//    val logger = logger()
//
//    @Auth(AppUserRole.USER)
//    @GraphQLDescription("산 상세 조회")
//    fun mountainDetail(
//        @Valid input: MountainDetailAppInput,
//    ): Boolean {
//        try {
//            mountainAppQueryService.findDetail(input.id)
//            return true
//        } catch (e: Throwable) {
//            logger.error(e.message, e)
//            throw e.toGraphQLException()
//        }
//    }
//
//    @Auth(AppUserRole.USER)
//    @GraphQLDescription("산 요약 정보")
//    fun mountainSummary(id: ID): MountainAppSummary =
//        catch { mountainAppQueryService.findOneSummary(id.toLong()).let(MountainAppSummary::by) }
//            .tapLeft { logger.error(it.message, it) }
//            .getOrThrow()
// }

@Controller
class MountainAppController(
    private val mountainAppQueryService: MountainAppQueryService,
    private val mountainAppMutationService: MountainAppMutationService,
) {
    val logger = logger()

    @QueryMapping
    fun mountainDetail(
        @Argument input: MountainDetailAppInput,
    ): Boolean {
        try {
            mountainAppQueryService.findDetail(input.id)
            return true
        } catch (e: Throwable) {
            logger.error(e.message, e)
            throw e.toGraphQLException()
        }
    }

    @QueryMapping
    fun mountainSummary(id: ID): MountainAppSummary =
        catch { mountainAppQueryService.findOneSummary(id.toLong()).let(MountainAppSummary::by) }
            .tapLeft { logger.error(it.message, it) }
            .getOrThrow()

    @MutationMapping
    fun registerMountain(
        @Valid input: CreateMountainAppInput,
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
