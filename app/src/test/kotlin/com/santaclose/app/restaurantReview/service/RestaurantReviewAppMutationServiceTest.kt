package com.santaclose.app.restaurantReview.service

import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurantReview.repository.RestaurantReviewAppRepository
import com.santaclose.app.restaurantReview.resolver.dto.CreateRestaurantReviewAppInput
import com.santaclose.app.restaurantReview.resolver.dto.RestaurantRatingDto
import com.santaclose.app.util.createAppMountain
import com.santaclose.app.util.createAppRestaurant
import com.santaclose.app.util.createAppUser
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
internal class RestaurantReviewAppMutationServiceTest
@Autowired
constructor(
  private val mountainAppRepository: MountainAppRepository,
  private val restaurantReviewAppRepository: RestaurantReviewAppRepository,
  private val restaurantRepository: RestaurantAppRepository,
  private val em: EntityManager
) {
  private val RestaurantReviewAppMutationService =
    RestaurantReviewAppMutationService(
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
        CreateRestaurantReviewAppInput(
          restaurant.id.toString(),
          mountainId = "-1",
          "title",
          "content",
          RestaurantRatingDto(1, 2, 3, 4, 5),
          mutableListOf()
        )

      // when
      val exception =
        shouldThrow<NoResultException> {
          RestaurantReviewAppMutationService.register(input, appUser.id)
        }

      // then
      exception.message shouldBe "유효하지 않은 mountainId 입니다."
    }

    @Test
    fun `restaurantId가 유효하지 않으면 NoResultException을 반환한다`() {
      // given
      val appUser = em.createAppUser()
      val mountain = em.createAppMountain(appUser)
      val input =
        CreateRestaurantReviewAppInput(
          restaurantId = "-1",
          mountain.id.toString(),
          "title",
          "content",
          RestaurantRatingDto(1, 2, 3, 4, 5),
          mutableListOf()
        )

      // when
      val exception =
        shouldThrow<NoResultException> {
          RestaurantReviewAppMutationService.register(input, appUser.id)
        }

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
        CreateRestaurantReviewAppInput(
          restaurant.id.toString(),
          mountain.id.toString(),
          "title",
          "content",
          RestaurantRatingDto(1, 2, 3, 4, 5),
          mutableListOf("url")
        )

      // when
      RestaurantReviewAppMutationService.register(input, appUser.id)

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
          rating.mood shouldBe input.rating.mood.toByte()
          rating.kind shouldBe input.rating.kind.toByte()
          rating.parkingSpace shouldBe input.rating.parkingSpace.toByte()
          rating.clean shouldBe input.rating.clean.toByte()
          rating.taste shouldBe input.rating.taste.toByte()
          images shouldBe input.images
        }
      }
    }
  }
}
