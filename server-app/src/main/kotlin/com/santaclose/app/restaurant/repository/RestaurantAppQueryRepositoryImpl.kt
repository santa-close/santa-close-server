package com.santaclose.app.restaurant.repository

import arrow.core.Either
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import com.santaclose.app.restaurant.repository.dto.RestaurantLocationDto
import com.santaclose.lib.entity.location.Location
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainRestaurant.MountainRestaurant
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.web.exception.DomainError.DBFailure
import com.santaclose.lib.web.exception.catchDB
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class RestaurantAppQueryRepositoryImpl(
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) :
    RestaurantAppQueryRepository {

    override fun findOneWithLocation(id: Long): Either<DBFailure, Restaurant> =
        Either.catchDB {
            val query = jpql {
                select(
                    entity(Restaurant::class),
                ).from(
                    entity(Restaurant::class),
                    innerFetchJoin(Restaurant::location),
                ).where(
                    path(Restaurant::id).eq(id),
                )
            }

            entityManager.createQuery(query, jpqlRenderContext).singleResult
        }

    override fun findLocationByMountain(mountainId: Long): Either<DBFailure, List<RestaurantLocationDto>> =
        Either.catchDB {
            val query = jpql {
                selectNew<RestaurantLocationDto>(
                    path(Restaurant::id),
                    path(Location::point),
                ).from(
                    entity(Mountain::class),
                    innerJoin(Mountain::mountainRestaurant),
                    innerJoin(MountainRestaurant::restaurant),
                    innerJoin(Restaurant::location),
                ).where(
                    path(Mountain::id).eq(mountainId),
                )
            }

            entityManager.createQuery(query, jpqlRenderContext).resultList
        }
}
