package com.santaclose.app.mountain.repository

import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createLocation
import com.santaclose.app.util.createMountain
import com.santaclose.app.util.createQueryFactory
import com.santaclose.lib.entity.location.Location
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager
import javax.persistence.NoResultException

@DataJpaTest
internal class MountainAppQueryRepositoryImplTest @Autowired constructor(
    private val em: EntityManager,
) {
    private val mountainAppQueryRepository = MountainAppQueryRepositoryImpl(em.createQueryFactory())

    @Nested
    inner class FindOneWithLocation {
        @Test
        fun `mountainId 값으로 mountain 과 location 을 조회한다`() {
            // given
            val appUser = em.createAppUser()
            val savedLocation = em.createLocation(Location.create(1.0, 2.0, "address", "postcode"))
            val mountain = em.createMountain(appUser, savedLocation)

            // when
            val result = mountainAppQueryRepository.findOneWithLocation(mountain.id)

            // then
            result.apply {
                id shouldBe mountain.id
                location shouldBe savedLocation
            }
        }

        @Test
        fun `mountainId 가 일치하는 mountain 이 없을 때 NoResultException 을 반환한다`() {
            // given
            val mountainId = -1L

            // when
            val exception =
                shouldThrow<NoResultException> {
                    mountainAppQueryRepository.findOneWithLocation(mountainId)
                }

            // then
            exception.message shouldBe "No entity found for query"
        }
    }
}
