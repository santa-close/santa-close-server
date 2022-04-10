package com.santaclose.app.mountain.service

import com.santaclose.app.mountain.resolver.dto.CreateMountainAppInput
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import javax.persistence.EntityManager

class MountainAppMutationService(
  private val em: EntityManager,
) {

  fun create(input: CreateMountainAppInput, userId: Long) {

    val mountain =
      Mountain(
        name = input.name,
        images = input.images.toMutableList(),
        management = input.management,
        altitude = input.altitude,
        appUser = em.getReference(AppUser::class.java, userId),
        location = Location.createPoint(input.longitude.toDouble(), input.latitude.toDouble()),
      )

    em.persist(mountain)
  }
}
