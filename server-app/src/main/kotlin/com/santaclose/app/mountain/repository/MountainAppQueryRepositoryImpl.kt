package com.santaclose.app.mountain.repository

import arrow.core.Either
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import com.santaclose.app.mountain.repository.dto.MountainLocationDto
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainRestaurant.MountainRestaurant
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.web.exception.DomainError.DBFailure
import com.santaclose.lib.web.exception.catchDB
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class MountainAppQueryRepositoryImpl(
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) : MountainAppQueryRepository {

    override fun findOneWithLocation(id: Long): Either<DBFailure, Mountain?> =
        Either.catchDB {
            val query = jpql {
                select(
                    entity(Mountain::class),
                ).from(
                    entity(Mountain::class),
                    innerFetchJoin(Mountain::location),
                ).where(
                    path(Mountain::id).eq(id),
                )
            }

            entityManager.createQuery(query, jpqlRenderContext).resultList.firstOrNull()
        }

    override fun findLocationByRestaurant(restaurantId: Long): Either<DBFailure, List<MountainLocationDto>> =
        Either.catchDB {
            val query = jpql {
                selectNew<MountainLocationDto>(
                    path(Mountain::id),
                    path(Location::point),
                ).from(
                    entity(Mountain::class),
                    innerJoin(Mountain::mountainRestaurant),
                    innerJoin(MountainRestaurant::restaurant),
                    innerJoin(Mountain::location),
                ).where(
                    path(Restaurant::id).eq(restaurantId),
                )
            }

            entityManager.createQuery(query, jpqlRenderContext).resultList
        }
}
