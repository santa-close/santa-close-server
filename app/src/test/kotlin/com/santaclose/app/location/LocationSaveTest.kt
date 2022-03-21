package com.santaclose.app.location

import com.santaclose.app.util.createAppUser
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
  fun `산 조회 시 좌표도 함께 가여올 수 있다`() {
    // given
    val appUser = em.createAppUser()
    val location = Location.createPoint(10.123, 20.345)
    var mountain = Mountain("name", "detail", appUser, location)
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
