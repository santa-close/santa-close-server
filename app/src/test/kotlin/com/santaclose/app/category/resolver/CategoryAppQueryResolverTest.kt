package com.santaclose.app.category.resolver

import com.santaclose.app.util.AppContextMocker
import com.santaclose.app.util.GraphqlBody
import com.santaclose.app.util.gqlRequest
import com.santaclose.app.util.withSuccess
import com.santaclose.lib.entity.appUser.type.AppUserRole
import java.io.File
import org.json.JSONObject
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
internal class CategoryAppQueryResolverTest
@Autowired
constructor(
  private val webTestClient: WebTestClient,
) : AppContextMocker() {
  @Nested
  inner class Categories {
    @Test
    fun `요청한 category 정보를 file 에 저장한다`() {
      // given
      val query =
        GraphqlBody(
          """query {
            |  categories {
            |    mountainDifficulty {
            |      code
            |      name
            |    }
            |  }
            |}
            """.trimMargin()
        )
      withMockUser(AppUserRole.USER)

      // when
      val response = webTestClient.gqlRequest(query)

      // then
      response.withSuccess("categories") {
        spec
          .returnResult()
          .responseBody
          ?.toString(Charsets.UTF_8)
          .let(::JSONObject)
          .toString(2)
          .let { File("src/main/resources/graphql/categories.json").writeText(it) }
      }
    }
  }
}
