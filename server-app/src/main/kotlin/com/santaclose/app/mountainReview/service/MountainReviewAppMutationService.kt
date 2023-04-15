package com.santaclose.app.mountainReview.service

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.mountain.repository.has
import com.santaclose.app.mountainReview.controller.dto.CreateMountainReviewAppInput
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainReview.MountainRating
import com.santaclose.lib.entity.mountainReview.MountainReview
import com.santaclose.lib.web.exception.DomainError
import com.santaclose.lib.web.exception.catchDB
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class MountainReviewAppMutationService(
    private val mountainAppRepository: MountainAppRepository,
    private val em: EntityManager,
) {

    @Transactional
    fun register(input: CreateMountainReviewAppInput, userId: Long): Either<DomainError, Unit> = either {
        val id = input.mountainId.toLong()
        val found = mountainAppRepository.has(id).bind()

        ensure(found) { DomainError.NotFound("유효하지 않은 mountainId 입니다: $id") }

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

        Either.catchDB { em.persist(review) }.bind()
    }
}
