package com.santaclose.app.mountainReview.service

import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.mountainReview.repository.MountainReviewAppRepository
import com.santaclose.app.mountainReview.resolver.dto.CreateMountainReviewAppInput
import com.santaclose.app.util.createAppUser
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainReview.type.MountainDifficulty.EASY
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
internal class MountainReviewAppMutationServiceTest
@Autowired
constructor(
  private val mountainAppRepository: MountainAppRepository,
  private val mountainReviewAppRepository: MountainReviewAppRepository,
  private val em: EntityManager,
) {
  private val mountainReviewAppMutationService =
    MountainReviewAppMutationService(mountainAppRepository, em)

  @Nested
  inner class Register {
    @Test
    fun `mountain id가 유효하지 않으면 NoResultException을 반환한다`() {
      // given
      val appUser = em.createAppUser()
      val location = Location.createPoint(10.123, 20.345).also { em.persist(it) }
      Mountain("name", "detail", appUser, location).also { em.persist(it) }
      val input =
        CreateMountainReviewAppInput("-1", "title", 1, 1, 1, 1, 1, 1, "content", emptyList(), EASY)

      // when
      val exception =
        shouldThrow<NoResultException> {
          mountainReviewAppMutationService.register(input, appUser.id)
        }

      // then
      exception.message shouldBe "유효하지 않은 mountainId 입니다."
    }

    @Test
    fun `mountain id가 유효하면 MountainReview를 생성한다`() {
      // given
      val appUser = em.createAppUser()
      val location = Location.createPoint(10.123, 20.345).also { em.persist(it) }
      val mountain =
        mountainAppRepository.save(Mountain("name", "detail", appUser, location)).also {
          em.persist(it)
        }
      val input =
        CreateMountainReviewAppInput(
          mountain.id.toString(),
          "title",
          1,
          2,
          3,
          4,
          5,
          3,
          "content",
          listOf("a", "b", "c"),
          EASY
        )

      // when
      mountainReviewAppMutationService.register(input, appUser.id)

      // then
      val mountainReviews = mountainReviewAppRepository.findAll()
      mountainReviews shouldHaveSize 1
      mountainReviews[0].apply {
        title shouldBe input.title
        rating.scenery shouldBe input.scenery.toByte()
        rating.tree shouldBe input.tree.toByte()
        rating.trail shouldBe input.trail.toByte()
        rating.parking shouldBe input.parking.toByte()
        rating.toilet shouldBe input.toilet.toByte()
        rating.traffic shouldBe input.traffic.toByte()
        content shouldBe input.content
        images shouldBe input.images
        difficulty shouldBe input.difficulty
      }
    }
  }
}
