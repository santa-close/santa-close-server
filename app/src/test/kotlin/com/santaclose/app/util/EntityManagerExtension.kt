package com.santaclose.app.util

import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreatorImpl
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactoryImpl
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.appUser.type.AppUserRole
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountain.type.MountainManagement
import com.santaclose.lib.entity.mountainReview.MountainRating
import com.santaclose.lib.entity.mountainReview.MountainReview
import com.santaclose.lib.entity.mountainReview.type.MountainDifficulty
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurant.RestaurantFoodType
import com.santaclose.lib.entity.restaurant.type.FoodType
import javax.persistence.EntityManager

fun EntityManager.createQueryFactory() =
  SpringDataQueryFactoryImpl(
    criteriaQueryCreator = CriteriaQueryCreatorImpl(this),
    subqueryCreator = SubqueryCreatorImpl()
  )

fun EntityManager.createAppUser(
  user: AppUser = AppUser("name", "email", "socialId", AppUserRole.USER)
): AppUser = user.also { this.persist(it) }

fun EntityManager.createRestaurant(
  appUser: AppUser,
  foodTypes: List<FoodType>,
  restaurant: Restaurant =
    Restaurant(
      "name",
      "description",
      mutableListOf(),
      foodTypes
        .map {
          RestaurantFoodType(
            restaurant = null,
            foodType = it,
          )
        }
        .toMutableList(),
      appUser,
      createLocation()
    )
) = restaurant.also { this.persist(it) }

fun EntityManager.createAppRestaurantFoodType(
  appUser: AppUser,
  restaurantFoodType: RestaurantFoodType =
    RestaurantFoodType(
      Restaurant("name", "description", mutableListOf(), null, appUser, createAppLocation()),
      FoodType.FOOD_COURT,
      appUser,
    )
) = restaurantFoodType.also { this.persist(it) }

fun EntityManager.createAppMountain(
  appUser: AppUser,
  location: Location = createLocation(),
  mountain: Mountain =
    Mountain("mountainName", mutableListOf(), MountainManagement.MUNICIPAL, 1000, appUser, location)
) = mountain.also { this.persist(it) }

fun EntityManager.createLocation(
  location: Location = Location.create(10.0, 20.0, "서울 중구 세종대로 110 서울특별시청", "04524")
) = location.also { this.persist(it) }

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
      appUser
    )
) = mountainReview.also { this.persist(it) }
