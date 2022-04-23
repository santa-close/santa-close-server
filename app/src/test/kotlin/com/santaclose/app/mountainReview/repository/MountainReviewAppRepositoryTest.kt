package com.santaclose.app.mountainReview.repository

import aws.smithy.kotlin.runtime.util.length
import com.santaclose.app.util.createMountain
import com.santaclose.app.util.createMountainReview
import com.santaclose.app.util.createQueryFactory
import com.santaclose.app.util.createUser
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainReview.MountainRating
import com.santaclose.lib.entity.mountainReview.MountainReview
import com.santaclose.lib.entity.mountainReview.type.MountainDifficulty.EASY
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import javax.persistence.EntityManager
import javax.persistence.PersistenceException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class MountainReviewAppRepositoryTest
@Autowired
constructor(
  private val mountainReviewAppRepository: MountainReviewAppRepository,
  private val em: EntityManager,
) {
  val mountainReviewAppQueryRepository =
    MountainReviewAppQueryRepositoryImpl(em.createQueryFactory())

  @Nested
  inner class Create {
    @Test
    fun `정상적으로 산 리뷰를 생성한다`() {
      // given
      val appUser = em.createAppUser()
      val location = em.createLocation()
      val mountain = em.createMountain(appUser, location)
      em.persist(mountain)

      val mountainRating = MountainRating(1, 2, 3, 4, 5, 3)
      val mountainReview =
        MountainReview(
          title = "title",
          content = "content",
          images = mutableListOf(),
          rating = mountainRating,
          difficulty = EASY,
          mountain = mountain,
          appUser = appUser,
        )

      // when
      mountainReviewAppRepository.save(mountainReview)

      // then
      mountainReview.id.shouldNotBeNull()
    }

    @Test
    fun `mountainReview 에서 mountainId 을 수정할 수 없다`() {
      // given
      val appUser = em.createAppUser()
      val location = em.createLocation()
      val mountain = em.createMountain(appUser, location)
      em.persist(mountain)

      val mountainRating = MountainRating(1, 2, 3, 4, 5, 3)
      val mountainReview =
        MountainReview(
          title = "title",
          content = "content",
          images = mutableListOf(),
          rating = mountainRating,
          difficulty = EASY,
          mountain = mountain,
          appUser = appUser,
        )
      mountainReviewAppRepository.save(mountainReview)
      val notExistId = 1000L

      // when
      mountainReview.mountain.id = notExistId

      // then
      shouldThrow<PersistenceException> {
        em.persist(mountainReview)
        em.flush()
      }
    }
  }

  @Nested
  inner class FindByMountainId {
    @Test
    fun `mountainId가 일치하는 리뷰를 반환한다`() {
      // given
      val count = 3
      val createdAppUser = em.createUser()
      val mountain = em.createMountain(createdAppUser)
      val otherMountain = em.createMountain(createdAppUser)
      repeat(count) {
        em.createMountainReview(createdAppUser, mountain)
        em.createMountainReview(createdAppUser, otherMountain)
      }

      // when
      val mountainReviews = mountainReviewAppQueryRepository.findAllByMountainId(mountain.id, 10)

      // then
      mountainReviews.length shouldBe count
      mountainReviews.map { it.appUser.id shouldBe createdAppUser.id }
    }

    @Test
    fun `mountainId가 일치하는 limit 수 만큼 리뷰를 반환한다`() {
      // given
      val limit = 3
      val createCounts = 10
      val appUser = em.createUser()
      val mountain = em.createMountain(appUser)
      repeat(createCounts) { em.createMountainReview(appUser, mountain) }

      // when
      val mountainReviews = mountainReviewAppQueryRepository.findAllByMountainId(mountain.id, limit)

      // then
      mountainReviews.length shouldBe limit
    }
  }
}
