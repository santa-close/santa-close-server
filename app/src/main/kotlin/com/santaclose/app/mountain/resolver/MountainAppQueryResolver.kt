package com.santaclose.app.mountain.resolver

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import com.santaclose.app.auth.directive.Auth
import com.santaclose.app.mountain.resolver.dto.MountainDetailAppInput
import com.santaclose.app.mountain.service.MountainAppQueryService
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.logger.logger
import com.santaclose.lib.web.error.toGraphQLException
import javax.validation.Valid
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

@Component
@Validated
class MountainAppQueryResolver(private val mountainAppQueryService: MountainAppQueryService) :
  Query {
  val logger = logger()

  @Auth(AppUserRole.USER)
  @GraphQLDescription("산 상세 조회")
  fun mountainDetail(
    @Valid input: MountainDetailAppInput,
  ): Boolean {
    try {
      mountainAppQueryService.findDetail(input.id)
      return true
    } catch (e: Throwable) {
      logger.error(e.message, e)
      throw e.toGraphQLException()
    }
  }
}
