package com.santaclose.app.mountain.service

import arrow.core.Either
import com.santaclose.app.mountain.controller.dto.CreateMountainAppInput
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountain.type.MountainManagement
import com.santaclose.lib.web.exception.DomainError.DBFailure
import com.santaclose.lib.web.exception.catchDB
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class MountainAppMutationService(
    private val em: EntityManager,
) {

    @Transactional
    fun register(input: CreateMountainAppInput, userId: Long): Either<DBFailure, Unit> =
        Either.catchDB {
            val mountain =
                Mountain(
                    name = input.name,
                    images = input.images.toMutableList(),
                    management = MountainManagement.NORMAL,
                    altitude = input.altitude,
                    appUser = em.getReference(AppUser::class.java, userId),
                    location = Location.create(input.longitude, input.latitude, input.address, input.postcode),
                )

            em.persist(mountain)
        }
}
