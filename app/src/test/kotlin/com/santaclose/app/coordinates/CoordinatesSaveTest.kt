package com.santaclose.app.coordinates

import com.santaclose.lib.entity.coordinate.Coordinates
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager

@DataJpaTest
class CoordinatesSaveTest @Autowired constructor(
    private val em: EntityManager
) {
    @Test
    fun `정상적으로 데이터를 저장한다`() {
        // given
        val coordinates = Coordinates.byMountain(1, 10.123, 20.345)

        // when
        em.persist(coordinates)

        // then
        em.flush()
        em.clear()
        val result = em.find(Coordinates::class.java, coordinates.id)
        result.shouldNotBeNull()
        result.point shouldBe coordinates.point
    }
}
