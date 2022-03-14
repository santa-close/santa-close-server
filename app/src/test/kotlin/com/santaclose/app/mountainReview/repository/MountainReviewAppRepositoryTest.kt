package com.santaclose.app.mountainReview.repository

import com.santaclose.app.util.createAppUser
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainReview.MountainRating
import com.santaclose.lib.entity.mountainReview.MountainReview
import com.santaclose.lib.entity.mountainReview.type.MountainDifficulty.EASY
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
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
  @Nested
  inner class Create {
    @Test
    fun `정상적으로 산 리뷰를 생성한다`() {
      // given
      val appUser = em.createAppUser()
      val mountain = Mountain("name", "detail", appUser)
      em.persist(mountain)

      val mountainRating = MountainRating(1, 2, 3, 4, 5, 3)
      val mountainReview =
        MountainReview.create(
          "title",
          "content",
          emptyList(),
          mountainRating,
          EASY,
          mountain,
          appUser
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
      val mountain = Mountain("name", "detail", appUser)
      em.persist(mountain)

      val mountainRating = MountainRating(1, 2, 3, 4, 5, 3)
      val mountainReview =
        MountainReview.create(
          "title",
          "content",
          emptyList(),
          mountainRating,
          EASY,
          mountain,
          appUser,
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
}
