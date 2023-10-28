package com.santaclose.app.mountainRestaurant.repository

import arrow.core.Either
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import com.santaclose.app.mountainRestaurant.repository.dto.LatestMountainDto
import com.santaclose.app.mountainRestaurant.repository.dto.LatestRestaurantDto
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainRestaurant.MountainRestaurant
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.web.exception.DomainError.DBFailure
import com.santaclose.lib.web.exception.catchDB
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class MountainRestaurantAppQueryRepositoryImpl(
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) : MountainRestaurantAppQueryRepository {
    override fun findMountainByRestaurant(id: Long, limit: Int): Either<DBFailure, List<LatestMountainDto>> =
        Either.catchDB {
            val query = jpql {
                selectNew<LatestMountainDto>(
                    path(Mountain::id),
                    path(Mountain::name),
                ).from(
                    entity(Mountain::class),
                    innerJoin(Mountain::mountainRestaurant),
                    innerJoin(MountainRestaurant::restaurant),
                ).where(
                    path(Restaurant::id).eq(id),
                ).orderBy(
                    path(Mountain::id).desc(),
                )
            }

            entityManager
                .createQuery(query, jpqlRenderContext)
                .apply { maxResults = limit }
                .resultList
        }

    override fun findRestaurantByMountain(id: Long, limit: Int): Either<DBFailure, List<LatestRestaurantDto>> =
        Either.catchDB {
            val query = jpql {
                selectNew<LatestRestaurantDto>(
                    path(Restaurant::id),
                    path(Restaurant::name),
                ).from(
                    entity(MountainRestaurant::class),
                    innerJoin(MountainRestaurant::mountain),
                    innerJoin(MountainRestaurant::restaurant),
                ).where(
                    path(Mountain::id).eq(id),
                ).orderBy(
                    path(Restaurant::id).desc(),
                )
            }

            entityManager
                .createQuery(query, jpqlRenderContext)
                .apply { maxResults = limit }
                .resultList
        }
}
