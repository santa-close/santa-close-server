package com.santaclose.app.mountain.service

import com.santaclose.app.mountain.repository.MountainAppQueryRepositoryImpl
import com.santaclose.app.mountainReview.repository.MountainReviewAppRepository
import com.santaclose.app.util.TestQueryFactory
import com.santaclose.app.util.createAppMountain
import com.santaclose.app.util.createAppUser
import com.santaclose.lib.web.toID
import io.kotest.matchers.shouldBe
import javax.persistence.EntityManager
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class MountainAppQueryServiceTest
@Autowired
constructor(
  private val mountainReviewAppRepository: MountainReviewAppRepository,
  private val entityManager: EntityManager,
) : TestQueryFactory() {
  private val mountainAppQueryRepository by lazy { MountainAppQueryRepositoryImpl(queryFactory) }
  private val mountainAppQueryService =
    MountainAppQueryService(mountainAppQueryRepository, mountainReviewAppRepository)

  @Nested
  inner class FindDetail {
    @Test
    fun `test`() {
      // given
      val user = entityManager.createAppUser()
      val mountain = entityManager.createAppMountain(user)

      // when
      // mountainAppQueryRepository.findOneWithLocation(mountain.id.toLong())
      mountainAppQueryService.findDetail(mountain.id.toID())

      // then
      mountain.apply { id shouldBe 1L }
    }
  }
}
