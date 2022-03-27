package com.santaclose.app.util

import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.restaurant.Restaurant
import javax.persistence.EntityManager

fun EntityManager.createAppUser(user: AppUser? = null): AppUser =
  (user ?: AppUser("name", "email", "socialId", AppUserRole.USER)).also { this.persist(it) }

fun EntityManager.createAppRestaurant(
  appUser: AppUser,
  restaurant: Restaurant =
    Restaurant("name", "description", mutableListOf(), appUser, createAppLocation())
) = restaurant.also { this.persist(it) }

fun EntityManager.createAppMountain(
  appUser: AppUser,
  mountain: Mountain = Mountain("mountainName", "mountainDetail", appUser, createAppLocation())
) = mountain.also { this.persist(it) }

fun EntityManager.createAppLocation(location: Location = Location.createPoint(1.1, 2.2)) =
  location.also { this.persist(it) }
