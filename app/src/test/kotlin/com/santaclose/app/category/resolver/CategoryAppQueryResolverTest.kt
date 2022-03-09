package com.santaclose.app.category.resolver

import com.santaclose.app.util.AppContextMocker
import com.santaclose.app.util.QueryInput
import com.santaclose.app.util.query
import com.santaclose.app.util.withSuccess
import com.santaclose.lib.entity.appUser.type.AppUserRole
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
internal class CategoryAppQueryResolverTest @Autowired constructor(
    private val webTestClient: WebTestClient,
) : AppContextMocker() {
    @Nested
    inner class Categories {
        @Test
        fun `요청한 category 정보를 가져온다`() {
            // given
            val query = QueryInput(
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
            val response = webTestClient.query(query)

            // then
            response.withSuccess("categories") {
                expect("mountainDifficulty").isNotEmpty
            }
        }
    }
}
