package com.santaclose.app.mountainReview.resolver

import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.mountainReview.service.MountainReviewAppMutationService
import com.santaclose.app.util.AppContextMocker
import com.santaclose.app.util.QueryInput
import com.santaclose.app.util.query
import com.santaclose.app.util.withError
import com.santaclose.app.util.withSuccess
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.web.error.GraphqlErrorCode
import io.mockk.every
import io.mockk.justRun
import javax.persistence.NoResultException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
internal class MountainReviewAppMutationResolverTest
@Autowired
constructor(
  private val webTestClient: WebTestClient,
  @MockkBean private val mountainReviewAppMutationService: MountainReviewAppMutationService,
) : AppContextMocker() {

  @Nested
  inner class CreateMountainReview {
    @Test
    fun `mountainId 가 유효하지 않으면 NOT FOUND 를 반환한다`() {
      // given
      val query =
        QueryInput(
          """mutation {
                |  createMountainReview(input: { 
                |    mountainId: "1" 
                |    title: "title"
                |    parking: 5
                |    scenery: 5
                |    toilet: 5
                |    traffic: 5
                |    trail: 5
                |    tree: 5
                |    content: "Good~"
                |    images: []
                |    difficulty: HARD
                |  })
                |}
                """.trimMargin()
        )
      every { mountainReviewAppMutationService.register(any(), any()) } throws
        NoResultException("no result")
      withMockUser(AppUserRole.USER)

      // when
      val response = webTestClient.query(query)

      // then
      response.withError(GraphqlErrorCode.NOT_FOUND, "no result")
    }

    @Test
    fun `정상적으로 생성한다`() {
      // given
      val query =
        QueryInput(
          """mutation {
                |  createMountainReview(input: { 
                |    mountainId: "1" 
                |    title: "title"
                |    parking: 5
                |    scenery: 5
                |    toilet: 5
                |    traffic: 5
                |    trail: 5
                |    tree: 5
                |    content: "Good~"
                |    images: ["a"]
                |    difficulty: NORMAL
                |  })
                |}
                """.trimMargin()
        )
      val session = withMockUser(AppUserRole.USER)
      justRun { mountainReviewAppMutationService.register(any(), session.id) }

      // when
      val response = webTestClient.query(query)

      // then
      response.withSuccess("createMountainReview")
    }
  }
}
