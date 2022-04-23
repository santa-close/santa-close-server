package com.santaclose.app.location

import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createMountain
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import javax.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class LocationSaveTest @Autowired constructor(private val em: EntityManager) {
  @Test
  fun `산 조회 시 좌표도 함께 가져올 수 있다`() {
    // given
    val appUser = em.createAppUser()
    val location = Location.create(10.123, 20.345, "서울 중구 세종대로 110 서울특별시청", "04524")
    var mountain = em.createMountain(appUser, location)
    em.persist(location)
    em.persist(mountain)
    em.flush()
    em.clear()

    // when
    val result = em.find(Mountain::class.java, mountain.id)

    // then
    result.shouldNotBeNull()
    result.location.point shouldBe location.point
  }
}
