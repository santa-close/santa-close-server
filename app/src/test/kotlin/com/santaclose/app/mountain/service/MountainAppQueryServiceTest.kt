package com.santaclose.app.mountain.service

import com.expediagroup.graphql.generator.scalars.ID
import com.santaclose.app.mountain.repository.MountainAppQueryRepositoryImpl
import com.santaclose.app.mountainReview.repository.MountainReviewAppQueryRepositoryImpl
import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createMountain
import com.santaclose.app.util.createMountainReview
import com.santaclose.app.util.createQueryFactory
import com.santaclose.lib.web.toID
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class MountainAppQueryServiceTest
@Autowired
constructor(
  private val em: EntityManager,
) {
  private val mountainAppQueryRepository = MountainAppQueryRepositoryImpl(em.createQueryFactory())
  private val mountainReviewAppQueryRepository =
    MountainReviewAppQueryRepositoryImpl(em.createQueryFactory())
  private val mountainAppQueryService =
    MountainAppQueryService(mountainAppQueryRepository, mountainReviewAppQueryRepository)

  @Nested
  inner class FindDetail {
    @Test
    fun `산 상세 정보를 조회하여 반환한다`() {
      // given
      val user = em.createAppUser()
      val mountain = em.createMountain(user)
      em.createMountainReview(user, mountain)

      // when
      val result = mountainAppQueryService.findDetail(mountain.id.toID())

      // then
      result.apply {
        name shouldBe mountain.name
        address shouldBe mountain.location.address
      }
    }

    @Test
    fun `mountainId가 유효하지 않으면 NoResultException 이 발생한다`() {
      // given
      val id = ID("-1")

      // when
      val exception = shouldThrow<NoResultException> { mountainAppQueryService.findDetail(id) }

      // then
      exception.message shouldBe "No entity found for query"
    }
  }
}
