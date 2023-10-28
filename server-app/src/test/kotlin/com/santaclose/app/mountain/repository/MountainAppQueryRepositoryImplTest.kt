package com.santaclose.app.mountain.repository

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createLocation
import com.santaclose.app.util.createMountain
import com.santaclose.app.util.createMountainRestaurant
import com.santaclose.app.util.createRestaurant
import com.santaclose.lib.entity.location.Location
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class MountainAppQueryRepositoryImplTest @Autowired constructor(
    private val em: EntityManager,
) {
    private val mountainAppQueryRepository = MountainAppQueryRepositoryImpl(em, JpqlRenderContext())

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
            result.shouldBeRight().apply {
                this.shouldNotBeNull()
                id shouldBe mountain.id
                location shouldBe savedLocation
            }
        }

        @Test
        fun `mountainId 가 일치하는 mountain 이 없을 때 null 을 반환한다`() {
            // given
            val mountainId = -1L

            // when/then
            mountainAppQueryRepository.findOneWithLocation(mountainId) shouldBeRight null
        }
    }

    @Nested
    inner class FindLocationByRestaurant {
        @Test
        fun `주어진 식당과 연결된 산좌표를 가져온다`() {
            // given
            val appUser = em.createAppUser()
            val mountain = em.createMountain(appUser)
            val restaurant = em.createRestaurant(appUser)
            em.createMountainRestaurant(mountain, restaurant)

            // when
            val result = mountainAppQueryRepository.findLocationByRestaurant(restaurant.id)

            // then
            result.shouldBeRight().apply {
                this shouldHaveSize 1
                first().point.coordinates[0] shouldBe mountain.location.point.coordinate
            }
        }
    }
}
