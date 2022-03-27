package com.santaclose.app.restaurantReview.service

import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurantReview.repository.RestaurantReviewAppRepository
import com.santaclose.app.restaurantReview.resolver.dto.CreateRestaurantReviewInput
import com.santaclose.app.util.createAppMountain
import com.santaclose.app.util.createAppRestaurant
import com.santaclose.app.util.createAppUser
import com.santaclose.lib.entity.restaurantReview.RestaurantRating
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class RestaurantReviewAppServiceTest
@Autowired
constructor(
  private val mountainAppRepository: MountainAppRepository,
  private val restaurantReviewAppRepository: RestaurantReviewAppRepository,
  private val restaurantRepository: RestaurantAppRepository,
  private val em: EntityManager
) {
  private val RestaurantReviewAppService =
    RestaurantReviewAppService(
      mountainAppRepository,
      restaurantReviewAppRepository,
      restaurantRepository,
      em
    )

  @Nested
  inner class Register {
    @Test
    fun `mountainId가 유효하지 않으면 NoResultException을 반환한다`() {
      // given
      val appUser = em.createAppUser()
      val restaurant = em.createAppRestaurant(appUser)
      val input =
        CreateRestaurantReviewInput(
          restaurant.id.toString(),
          mountainId = "-1",
          "title",
          "content",
          RestaurantRating(1, 2, 3, 4, 5),
          mutableListOf()
        )

      // when
      val exception =
        shouldThrow<NoResultException> { RestaurantReviewAppService.register(input, appUser.id) }

      // then
      exception.message shouldBe "유효하지 않은 mountainId 입니다."
    }

    @Test
    fun `restaurantId가 유효하지 않으면 NoResultException을 반환한다`() {
      // given
      val appUser = em.createAppUser()
      val mountain = em.createAppMountain(appUser)
      val input =
        CreateRestaurantReviewInput(
          restaurantId = "-1",
          mountain.id.toString(),
          "title",
          "content",
          RestaurantRating(1, 2, 3, 4, 5),
          mutableListOf()
        )

      // when
      val exception =
        shouldThrow<NoResultException> { RestaurantReviewAppService.register(input, appUser.id) }

      // then
      exception.message shouldBe "유효하지 않은 restaurantId 입니다."
    }

    @Test
    fun `RestaurantReview를 저장한다`() {
      // given
      val appUser = em.createAppUser()
      val restaurant = em.createAppRestaurant(appUser)
      val mountain = em.createAppMountain(appUser)
      val input =
        CreateRestaurantReviewInput(
          restaurant.id.toString(),
          mountain.id.toString(),
          "title",
          "content",
          RestaurantRating(1, 2, 3, 4, 5),
          mutableListOf("url")
        )

      // when
      RestaurantReviewAppService.register(input, appUser.id)

      // then
      val restaurantReview = restaurantReviewAppRepository.findAll()
      restaurantReview shouldHaveSize 1
      restaurantReview.forEach {
        it.apply {
          restaurant.id shouldBe input.restaurantId.toLong()
          mountain.id shouldBe input.mountainId.toLong()
          appUser.id shouldBe appUser.id
          title shouldBe input.title
          content shouldBe input.content
          rating shouldBe input.rating
          images shouldBe input.images
        }
      }
    }
  }
}
