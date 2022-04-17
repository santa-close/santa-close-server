package com.santaclose.app.location

import com.santaclose.app.util.createAppLocation
import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createMountain
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import javax.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LocationSaveTest @Autowired constructor(private val em: EntityManager) {
  @Test
  fun `산 조회 시 좌표도 함께 가져올 수 있다`() {
    // given
    val appUser = em.createAppUser()
    val location = Location.create(10.123, 20.345, "서울 중구 세종대로 110 서울특별시청", "04524")
    val mountain = em.createMountain(appUser, location)

    // when
    val result = em.find(Mountain::class.java, mountain.id)

    // then
    result.shouldNotBeNull()
    result.location.point shouldBe location.point
  }

  @Test
  fun `주어진 영역 안의 location 을 가져온다`() {
    // given
    val location = em.createAppLocation(Location.create(1.0, 1.0))
    em.createAppLocation(Location.create(3.0, 3.0))
    val factory = GeometryFactory()
    val rectangle =
      factory.createPolygon(
        arrayOf(
          Coordinate(0.0, 0.0),
          Coordinate(0.0, 2.0),
          Coordinate(2.0, 2.0),
          Coordinate(2.0, 0.0),
          Coordinate(0.0, 0.0)
        )
      )

    // when
    val result =
      em.createQuery(
          "SELECT l FROM Location l WHERE within(l.point, :area) = true",
          Location::class.java
        )
        .setParameter("area", rectangle)
        .resultList

    // then
    result.map { it.point } shouldBe listOf(location.point)
  }
}
