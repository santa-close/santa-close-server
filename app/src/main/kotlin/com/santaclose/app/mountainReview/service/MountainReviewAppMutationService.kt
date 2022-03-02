package com.santaclose.app.mountainReview.service

import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.mountainReview.repository.MountainReviewAppRepository
import com.santaclose.app.mountainReview.resolver.dto.CreateMountainReviewAppInput
import com.santaclose.lib.entity.mountainReview.MountainReview
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.NoResultException

@Service
class MountainReviewAppMutationService(
    private val mountainAppRepository: MountainAppRepository,
    private val mountainReviewAppRepository: MountainReviewAppRepository,
) {
    fun register(input: CreateMountainReviewAppInput) {
        val mountain =
            mountainAppRepository.findByIdOrNull(input.mountainId.toLong())
                ?: throw NoResultException("유효하지 않은 mountainId 입니다.")

        mountainReviewAppRepository.save(
            MountainReview.create(
                title = input.title,
                scenery = input.scenery.toByte(),
                facility = input.facility.toByte(),
                traffic = input.traffic.toByte(),
                content = input.content,
                mountain = mountain,
            )
        )
    }
}
