package com.santaclose.app.restaurant.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.santaclose.app.restaurant.repository.dto.RestaurantLocationDto
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainRestaurant.MountainRestaurant
import com.santaclose.lib.entity.restaurant.Restaurant
import jakarta.persistence.criteria.JoinType
import org.springframework.stereotype.Repository

@Repository
class RestaurantAppQueryRepositoryImpl(private val springDataQueryFactory: SpringDataQueryFactory) :
    RestaurantAppQueryRepository {

    override fun findOneWithLocation(id: Long): Restaurant =
        springDataQueryFactory.singleQuery {
            select(entity(Restaurant::class))
            from(Restaurant::class)
            where(col(Restaurant::id).equal(id))
            fetch(Restaurant::location, JoinType.INNER)
        }

    override fun findLocationByMountain(mountainId: Long): List<RestaurantLocationDto> =
        this.springDataQueryFactory.listQuery {
            selectMulti(col(Restaurant::id), col(Location::point))
            from(Mountain::class)
            join(Mountain::mountainRestaurant, JoinType.INNER)
            join(MountainRestaurant::restaurant, JoinType.INNER)
            join(Restaurant::location, JoinType.INNER)
            where(col(Mountain::id).equal(mountainId))
        }
}
