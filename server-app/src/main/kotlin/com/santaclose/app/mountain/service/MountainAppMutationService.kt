package com.santaclose.app.mountain.service

import com.santaclose.app.mountain.controller.dto.CreateMountainAppInput
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountain.type.MountainManagement
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Service
class MountainAppMutationService(
    private val em: EntityManager,
) {

    @Transactional
    fun register(input: CreateMountainAppInput, userId: Long) {
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
