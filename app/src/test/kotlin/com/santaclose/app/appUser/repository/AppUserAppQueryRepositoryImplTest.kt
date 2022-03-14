package com.santaclose.app.appUser.repository

import com.santaclose.app.util.TestQueryFactory
import com.santaclose.lib.entity.appUser.AppUser
import io.kotest.assertions.arrow.core.shouldBeRight
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class AppUserAppQueryRepositoryImplTest : TestQueryFactory() {
  private val appUserAppRepository by lazy { AppUserAppQueryRepositoryImpl(queryFactory) }

  @Nested
  inner class FindBySocialId {
    @Test
    fun `존재하지 않는 id 로 요청시 None 을 반환한다`() {
      // when
      val result = appUserAppRepository.findBySocialId("not exist")

      // then
      result shouldBeRight null
    }

    @Test
    fun `정상적으로 사용자를 가져온다`() {
      // given
      val appUser = AppUser.signUp("name", "email", "12345")
      em.persist(appUser)

      // when
      val result = appUserAppRepository.findBySocialId("12345")

      // then
      result shouldBeRight appUser
    }
  }
}
