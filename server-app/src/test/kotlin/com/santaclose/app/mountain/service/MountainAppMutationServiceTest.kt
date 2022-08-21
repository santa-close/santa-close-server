package com.santaclose.app.mountain.service

import com.santaclose.app.mountain.controller.dto.CreateMountainAppInput
import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.util.createAppUser
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager

@DataJpaTest
internal class MountainAppMutationServiceTest @Autowired constructor(
    private val mountainAppRepository: MountainAppRepository,
    private val em: EntityManager,
) {
    private val mountainAppMutationService = MountainAppMutationService(em)

    @Nested
    inner class Register {
        @Test
        fun `입력한 정보로 산이 등록한다`() {
            // given
            val appUser = em.createAppUser()
            val input =
                CreateMountainAppInput(
                    name = "name",
                    images = mutableListOf("image"),
                    altitude = 100,
                    longitude = 100.0,
                    latitude = 100.0,
                    address = "address",
                    postcode = "postcode",
                )

            // when
            mountainAppMutationService.register(input, appUser.id)

            // then
            val mountain = mountainAppRepository.findAll()
            mountain shouldHaveSize 1
            mountain[0].apply {
                name shouldBe input.name
                images shouldBe input.images
                altitude shouldBe input.altitude
                this.appUser.id shouldBe appUser.id
                location.point.x shouldBe input.latitude
                location.point.y shouldBe input.longitude
            }
        }
    }
}
