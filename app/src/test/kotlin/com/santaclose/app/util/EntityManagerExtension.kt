package com.santaclose.app.util

import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountain.type.MountainManagement
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurant.type.FoodType
import javax.persistence.EntityManager

fun EntityManager.createAppUser(
  user: AppUser = AppUser("name", "email", "socialId", AppUserRole.USER)
): AppUser = user.also { this.persist(it) }

fun EntityManager.createRestaurant(
  appUser: AppUser,
  restaurant: Restaurant =
    Restaurant("name", "description", mutableListOf(), FoodType.ASIA, appUser, createLocation())
) = restaurant.also { this.persist(it) }

fun EntityManager.createMountain(
  appUser: AppUser,
  location: Location = createLocation(),
  mountain: Mountain =
    Mountain("mountainName", mutableListOf(), MountainManagement.MUNICIPAL, 1000, appUser, location)
) = mountain.also { this.persist(it) }

fun EntityManager.createLocation(
  location: Location = Location.create(10.0, 20.0, "서울 중구 세종대로 110 서울특별시청", "04524")
) = location.also { this.persist(it) }
