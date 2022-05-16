package com.santaclose.app.restaurant.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.santaclose.lib.entity.restaurant.Restaurant
import javax.persistence.criteria.JoinType
import org.springframework.stereotype.Repository

@Repository
class RestaurantAppQueryRepositoryImpl(private val springDataQueryFactory: SpringDataQueryFactory) :
  RestaurantAppQueryRepository {
  override fun findOneWithLocation(id: Long): Restaurant =
    springDataQueryFactory.singleQuery {
      select(entity(Restaurant::class))
      from(Restaurant::class)
      fetch(Restaurant::location, JoinType.INNER)
      where(col(Restaurant::id).equal(id))
    }
}
