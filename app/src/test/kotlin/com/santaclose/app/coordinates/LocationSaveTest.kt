package com.santaclose.app.coordinates

import com.santaclose.lib.entity.coordinate.Location
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import javax.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class LocationSaveTest @Autowired constructor(private val em: EntityManager) {
  @Test
  fun `정상적으로 데이터를 저장한다`() {
    // given
    val location = Location.byMountain(1, 10.123, 20.345)

    // when
    em.persist(location)

    // then
    em.flush()
    em.clear()
    val result = em.find(Location::class.java, location.id)
    result.shouldNotBeNull()
    result.point shouldBe location.point
  }
}
