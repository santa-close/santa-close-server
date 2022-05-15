package com.santaclose.app.restaurant.resolver

import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.restaurant.service.RestaurantAppMutationService
import com.santaclose.app.util.AppContextMocker
import com.santaclose.app.util.GraphqlBody
import com.santaclose.app.util.gqlRequest
import com.santaclose.app.util.withSuccess
import com.santaclose.lib.entity.appUser.type.AppUserRole
import io.mockk.justRun
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
internal class RestaurantAppMutationResolverTest
@Autowired
constructor(
  private val webTestClient: WebTestClient,
  @MockkBean private val restaurantAppMutationService: RestaurantAppMutationService
) : AppContextMocker() {

  @Nested
  inner class Register {
    @Test
    fun `정상적으로 사용자가 식당을 등록한다 - 성공`() {
      // given
      val query =
        GraphqlBody(
          """mutation {
            |  createRestaurant(input: { 
            |    mountainId: "1"
            |    name: "식당 이름"
            |    description: "식당 설명"
            |    images: ["image1", "image2", "image3"]
            |    foodTypes: [ASIA, KOREAN, PUB]
            |    longitude: 100.00
            |    latitude: 60.00
            |    address: "주소명"
            |    postcode: "우편번호"
            |  })
            |}
            """.trimMargin()
        )
      justRun { restaurantAppMutationService.createRestaurant(any(), any()) }
      withMockUser(AppUserRole.USER)

      // when
      val response = webTestClient.gqlRequest(query)

      // then
      response.withSuccess("createRestaurant")
    }
  }
}
