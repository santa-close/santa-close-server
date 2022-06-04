package com.santaclose.app.mountainReview.repository

import com.santaclose.app.mountainReview.repository.dto.MountainRatingAverageDto
import com.santaclose.app.util.createAppUser
import com.santaclose.app.util.createMountain
import com.santaclose.app.util.createMountainReview
import com.santaclose.app.util.createQueryFactory
import com.santaclose.lib.entity.mountainReview.MountainRating
import com.santaclose.lib.entity.mountainReview.MountainReview
import com.santaclose.lib.entity.mountainReview.type.MountainDifficulty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManager

@DataJpaTest
internal class MountainReviewAppQueryRepositoryImplTest @Autowired constructor(
    private val em: EntityManager,
) {
    private val mountainReviewAppQueryRepository =
        MountainReviewAppQueryRepositoryImpl(em.createQueryFactory())

    @Nested
    inner class FindMountainRatingAverages {
        @Test
        fun `산 후기의 점수 평균을 조회한다`() {
            // given
            val appUser = em.createAppUser()
            val mountain = em.createMountain(appUser)
            val count = 5
            repeat(count) {
                em.createMountainReview(
                    appUser,
                    mountain,
                    MountainReview(
                        "title",
                        MountainRating(scenery = 1, tree = 2, trail = 3, parking = 4, toilet = 5, traffic = 1),
                        "",
                        mutableListOf(),
                        MountainDifficulty.HARD,
                        mountain,
                        appUser
                    )
                )
            }

            // when
            val dto = mountainReviewAppQueryRepository.findMountainRatingAverages(mountain.id)

            // then
            dto.apply {
                scenery shouldBe 1
                tree shouldBe 2
                trail shouldBe 3
                parking shouldBe 4
                toilet shouldBe 5
                traffic shouldBe 1
                average shouldBe 16.0 / 6
                totalCount shouldBe count
            }
        }

        @Test
        fun `산 후기가 하나도 없는 경우 empty 를 반환한다`() {
            // given
            val appUser = em.createAppUser()
            val mountain = em.createMountain(appUser)

            // when
            val dto = mountainReviewAppQueryRepository.findMountainRatingAverages(mountain.id)

            // then
            dto shouldBe MountainRatingAverageDto.empty
        }
    }
}
