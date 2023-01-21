package com.santaclose.app.location.repository

import com.santaclose.app.util.createLocation
import com.santaclose.lib.entity.location.Location
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class LocationAppRepositoryTest @Autowired constructor(
    private val locationAppRepository: LocationAppRepository,
    private val em: EntityManager,
) {
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
        val result = locationAppRepository.findIdsByArea(rectangle)

        // then
        result shouldHaveSize 1
        result.first().id shouldBe location.id
        result.first().point shouldBe location.point
    }
}
