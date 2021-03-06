package com.santaclose.app.mountainRestaurant.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.santaclose.app.mountainRestaurant.repository.dto.LatestMountainDto
import com.santaclose.app.mountainRestaurant.repository.dto.LatestRestaurantDto
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainRestaurant.MountainRestaurant
import com.santaclose.lib.entity.restaurant.Restaurant
import org.springframework.stereotype.Repository
import javax.persistence.criteria.JoinType

@Repository
class MountainRestaurantAppQueryRepositoryImpl(
    private val springDataQueryFactory: SpringDataQueryFactory,
) : MountainRestaurantAppQueryRepository {
    override fun findMountainByRestaurant(id: Long, limit: Int): List<LatestMountainDto> =
        springDataQueryFactory.listQuery {
            selectMulti(col(Mountain::id), col(Mountain::name))
            from(MountainRestaurant::class)
            join(MountainRestaurant::mountain, JoinType.INNER)
            join(MountainRestaurant::restaurant, JoinType.INNER)
            where(col(Restaurant::id).equal(id))
            orderBy(col(Mountain::id).desc())
            limit(limit)
        }

    override fun findRestaurantByMountain(id: Long, limit: Int): List<LatestRestaurantDto> =
        springDataQueryFactory.listQuery {
            selectMulti(col(Restaurant::id), col(Restaurant::name))
            from(MountainRestaurant::class)
            join(MountainRestaurant::mountain, JoinType.INNER)
            join(MountainRestaurant::restaurant, JoinType.INNER)
            where(col(Mountain::id).equal(id))
            orderBy(col(Restaurant::id).desc())
            limit(limit)
        }
}
