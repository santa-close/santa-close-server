package com.santaclose.app.mountainReview.repository

import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainReview.MountainReview
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class MountainReviewAppRepositoryTest @Autowired constructor(
    private val mountainReviewAppRepository: MountainReviewAppRepository,
    private val mountainAppRepository: MountainAppRepository,
) {
    @Nested
    inner class Create {
        @Test
        fun `정상적으로 산 리뷰를 생성한다`() {
            // given
            val mountain = Mountain("name", "detail")
            mountainAppRepository.save(mountain)
            val mountainReview = MountainReview.create("title", 1, 1, 1, 1, 1, 1, "content", mountain)

            // when
            mountainReviewAppRepository.save(mountainReview)

            // then
            mountainReview.id.shouldNotBeNull()
        }

        @Test
        fun `mountainReview 에서 mountainId 을 수정할 수 없다`() {
            // given
            val mountain = Mountain("name", "detail")
            mountainAppRepository.save(mountain)
            val mountainReview = MountainReview.create("title", 1, 1, 1, 1, 1, 1, "content", mountain)
            mountainReviewAppRepository.save(mountainReview)
            val notExistId = 1000L

            // when
            mountainReview.mountain.id = notExistId

            // then
            shouldThrow<Throwable> { mountainAppRepository.existsById(notExistId) }
        }
    }
}
