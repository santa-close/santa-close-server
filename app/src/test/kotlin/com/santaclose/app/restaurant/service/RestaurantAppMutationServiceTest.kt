package com.santaclose.app.restaurant.service

import com.santaclose.app.mountain.repository.MountainAppRepository
import com.santaclose.app.restaurant.repository.RestaurantAppRepository
import com.santaclose.app.restaurant.repository.RestaurantFoodTypeAppRepository
import javax.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class RestaurantAppMutationServiceTest
@Autowired
constructor(
  private val restaurantRepository: RestaurantAppRepository,
  private val mountainAppRepository: MountainAppRepository,
  private val restaurantFoodTypeAppRepository: RestaurantFoodTypeAppRepository,
  private val em: EntityManager
) {

  // TODO: 테스트코드 작성
}
