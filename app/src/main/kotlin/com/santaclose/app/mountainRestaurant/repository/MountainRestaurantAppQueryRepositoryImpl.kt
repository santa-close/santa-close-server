package com.santaclose.app.mountainRestaurant.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.santaclose.app.mountainRestaurant.repository.dto.MountainByRestaurantDto
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainRestaurant.MountainRestaurant
import com.santaclose.lib.entity.restaurant.Restaurant
import javax.persistence.criteria.JoinType
import org.springframework.stereotype.Repository

@Repository
class MountainRestaurantAppQueryRepositoryImpl(
  private val springDataQueryFactory: SpringDataQueryFactory
) : MountainRestaurantAppQueryRepository {
  override fun findMountainByRestaurant(id: Long, limit: Int): List<MountainByRestaurantDto> =
    springDataQueryFactory.listQuery {
      selectMulti(col(Mountain::id), col(Mountain::name))
      from(MountainRestaurant::class)
      join(MountainRestaurant::mountain, JoinType.INNER)
      join(MountainRestaurant::restaurant, JoinType.INNER)
      where(col(Restaurant::id).equal(id))
      orderBy(col(Mountain::id).desc())
      limit(limit)
    }
}
