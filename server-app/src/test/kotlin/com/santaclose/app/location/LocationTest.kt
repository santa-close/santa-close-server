package com.santaclose.app.location

import com.santaclose.app.util.createLocation
import com.santaclose.lib.entity.location.Location
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import javax.persistence.EntityManager

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class LocationTest @Autowired constructor(private val em: EntityManager) {
    @Test
    fun `주어진 영역 안의 location 을 가져온다`() {
        // given
        val location = em.createLocation(Location.create(1.0, 1.0))
        em.createLocation(Location.create(3.0, 3.0))
        val factory = GeometryFactory()
        val rectangle =
            factory.createPolygon(
                arrayOf(
                    Coordinate(0.0, 0.0),
                    Coordinate(0.0, 2.0),
                    Coordinate(2.0, 2.0),
                    Coordinate(2.0, 0.0),
                    Coordinate(0.0, 0.0),
                ),
            )

        // when
        val result =
            em.createQuery(
                "SELECT l FROM Location l WHERE within(l.point, :area) = true",
                Location::class.java,
            )
                .setParameter("area", rectangle)
                .resultList

        // then
        result.map { it.point } shouldBe listOf(location.point)
    }
}
