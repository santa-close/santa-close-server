package com.santaclose.app.mountain.repository

import arrow.core.Either
import arrow.core.recover
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.santaclose.app.mountain.repository.dto.MountainLocationDto
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainRestaurant.MountainRestaurant
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.web.exception.DomainError.DBFailure
import jakarta.persistence.NoResultException
import jakarta.persistence.criteria.JoinType
import org.springframework.stereotype.Repository

@Repository
class MountainAppQueryRepositoryImpl(
    private val springDataQueryFactory: SpringDataQueryFactory,
) : MountainAppQueryRepository {

    override fun findOneWithLocation(id: Long): Either<DBFailure, Mountain?> =
        Either.catch {
            springDataQueryFactory.singleQuery {
                select(entity(Mountain::class))
                from(Mountain::class)
                fetch(Mountain::location, JoinType.INNER)
                where(col(Mountain::id).equal(id))
            }
        }.recover {
            if (it is NoResultException) {
                null
            } else {
                raise(DBFailure(it))
            }
        }

    override fun findLocationByRestaurant(restaurantId: Long): List<MountainLocationDto> =
        springDataQueryFactory.listQuery {
            selectMulti(col(Mountain::id), col(Location::point))
            from(Mountain::class)
            join(Mountain::mountainRestaurant, JoinType.INNER)
            join(MountainRestaurant::restaurant, JoinType.INNER)
            join(Mountain::location, JoinType.INNER)
            where(col(Restaurant::id).equal(restaurantId))
        }
}
