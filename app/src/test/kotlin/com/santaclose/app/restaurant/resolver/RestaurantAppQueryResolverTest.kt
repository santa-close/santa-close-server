// package com.santaclose.app.restaurant.resolver
//
// import com.ninjasquad.springmockk.MockkBean
// import com.santaclose.app.restaurant.resolver.dto.RestaurantAppDetail
// import com.santaclose.app.restaurant.service.RestaurantAppQueryService
// import com.santaclose.app.restaurantReview.resolver.dto.RestaurantRatingAverage
// import com.santaclose.app.util.AppContextMocker
// import com.santaclose.app.util.GraphqlBody
// import com.santaclose.app.util.gqlRequest
// import com.santaclose.app.util.withSuccess
// import com.santaclose.lib.entity.appUser.type.AppUserRole
// import com.santaclose.lib.entity.restaurant.type.FoodType
// import com.santaclose.lib.web.toID
// import io.mockk.every
// import org.junit.jupiter.api.Nested
// import org.junit.jupiter.api.Test
// import org.springframework.beans.factory.annotation.Autowired
// import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
// import org.springframework.boot.test.context.SpringBootTest
// import org.springframework.test.web.reactive.server.WebTestClient
//
// @SpringBootTest
// @AutoConfigureWebTestClient
// internal class RestaurantAppQueryResolverTest
// @Autowired
// constructor(
//  private val webTestClient: WebTestClient,
//  @MockkBean private val restaurantAppQueryService: RestaurantAppQueryService
// ) : AppContextMocker() {
//  @Nested
//  inner class Detail {
//    @Test
//    fun `유저가 식당을 조회한다`() {
//      // given
//      val query =
//        GraphqlBody(
//          """query{
//              | restaurantDetail(input: { id: "1" }) {
//              |     address
//              |     foodType
//              |     name
//              |     mountains {
//              |       id
//              |       name
//              |     }
//              |     restaurantRatingAverage {
//              |         average
//              |         clean
//              |         kind
//              |         mood
//              |         parkingSpace
//              |         taste
//              |         totalCount
//              |     }
//              |     restaurantReviews: {
//              |         content
//              |         id
//              |         title
//              |     }
//              | }
//              |}
//          """.trimMargin()
//        )
//      every { restaurantAppQueryService.findDetail(any()) } returns
//        RestaurantAppDetail(
//          name = "name",
//          address = "address",
//          foodType = listOf(FoodType.FOOD_COURT),
//          restaurantRatingAverage =
//            RestaurantRatingAverage(1.0, 2.0, 3.0, 4.0, 5.0, 6L.toID(), 7.0),
//          restaurantReviews = listOf(),
//          mountains = listOf()
//        )
//      withMockUser(AppUserRole.USER)
//
//      // when
//      val response = webTestClient.gqlRequest(query)
//
//      // then
//      response.withSuccess("restaurantDetail")
//    }
//  }
// }
