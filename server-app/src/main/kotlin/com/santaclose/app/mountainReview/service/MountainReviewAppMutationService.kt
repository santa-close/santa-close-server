package com.santaclose.app.mountainReview.service

import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.mountainReview.controller.dto.CreateMountainReviewAppInput
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainReview.MountainRating
import com.santaclose.lib.entity.mountainReview.MountainReview
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.NoResultException

@Service
class MountainReviewAppMutationService(
    private val mountainAppRepository: MountainAppRepository,
    private val em: EntityManager,
) {
    fun register(input: CreateMountainReviewAppInput, userId: Long) {
        val id = input.mountainId.toLong()
        val isExist = mountainAppRepository.existsById(id)

        if (!isExist) {
            throw NoResultException("유효하지 않은 mountainId 입니다.")
        }

        val rating =
            MountainRating(
                scenery = input.scenery.toByte(),
                tree = input.tree.toByte(),
                trail = input.trail.toByte(),
                parking = input.parking.toByte(),
                toilet = input.toilet.toByte(),
                traffic = input.traffic.toByte(),
            )
        val review =
            MountainReview(
                title = input.title,
                content = input.content,
                images = input.images.toMutableList(),
                rating = rating,
                difficulty = input.difficulty,
                mountain = em.getReference(Mountain::class.java, id),
                appUser = em.getReference(AppUser::class.java, userId),
            )

        em.persist(review)
    }
}
