package com.santaclose.app.restaurant.resolver

import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.restaurant.service.RestaurantAppMutationService
import com.santaclose.app.util.AppContextMocker
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
  // TODO: 테스트코드 작성
}
