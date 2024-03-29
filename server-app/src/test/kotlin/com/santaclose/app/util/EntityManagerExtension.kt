package com.santaclose.app.util

import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountain.type.MountainManagement
import com.santaclose.lib.entity.mountainRestaurant.MountainRestaurant
import com.santaclose.lib.entity.mountainReview.MountainRating
import com.santaclose.lib.entity.mountainReview.MountainReview
import com.santaclose.lib.entity.mountainReview.type.MountainDifficulty
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurantReview.RestaurantRating
import com.santaclose.lib.entity.restaurantReview.RestaurantReview
import com.santaclose.lib.entity.restaurantReview.type.PriceComment
import jakarta.persistence.EntityManager

inline fun <reified T> EntityManager.findAll(): List<T> {
    val query = criteriaBuilder.createQuery(T::class.java)
    query.select(query.from(T::class.java))

    return createQuery(query).resultList
}

fun EntityManager.createAppUser(
    user: AppUser = AppUser("name", "email", "socialId", AppUserRole.USER),
): AppUser = user.also { persist(it) }

fun EntityManager.createRestaurant(
    appUser: AppUser,
    location: Location = createLocation(),
    restaurant: Restaurant =
        Restaurant("name", "description", mutableListOf(), mutableListOf(), appUser, location),
) = restaurant.also { this.persist(it) }

fun EntityManager.createMountain(
    appUser: AppUser,
    location: Location = createLocation(),
    mountain: Mountain =
        Mountain("mountainName", mutableListOf(), MountainManagement.MUNICIPAL, 1000, appUser, location),
) = mountain.also { persist(it) }

fun EntityManager.createLocation(
    location: Location = Location.create(10.0, 20.0, "서울 중구 세종대로 110 서울특별시청", "04524"),
) = location.also { persist(it) }

fun EntityManager.createMountainReview(
    appUser: AppUser,
    mountain: Mountain,
    mountainReview: MountainReview =
        MountainReview(
            "title",
            MountainRating(1, 2, 3, 4, 5, 5),
            "content",
            mutableListOf(),
            MountainDifficulty.HARD,
            mountain,
            appUser,
        ),
) = mountainReview.also { persist(it) }

fun EntityManager.createRestaurantReview(appUser: AppUser, restaurant: Restaurant) =
    RestaurantReview(
        "title",
        "content",
        PriceComment.IS_CHEAP,
        1,
        RestaurantRating(1, 2, 3, 4, 5),
        mutableListOf(),
        restaurant,
        appUser,
    )
        .also { persist(it) }

fun EntityManager.createMountainRestaurant(
    mountain: Mountain,
    restaurant: Restaurant,
) = MountainRestaurant(mountain, restaurant).also { persist(it) }
