package com.santaclose.app.restaurantReview.repository

import arrow.core.Either
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import com.santaclose.app.restaurantReview.repository.dto.LatestRestaurantReviewDto
import com.santaclose.app.restaurantReview.repository.dto.RestaurantRatingAverageDto
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurantReview.RestaurantRating
import com.santaclose.lib.entity.restaurantReview.RestaurantReview
import com.santaclose.lib.web.exception.DomainError.DBFailure
import com.santaclose.lib.web.exception.catchDB
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class RestaurantReviewAppQueryRepositoryImpl(
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) : RestaurantReviewAppQueryRepository {

    override fun findAllByRestaurant(
        restaurantId: Long,
        limit: Int,
    ): Either<DBFailure, List<LatestRestaurantReviewDto>> = Either.catchDB {
        val query = jpql {
            selectNew<LatestRestaurantReviewDto>(
                path(RestaurantReview::id),
                path(RestaurantReview::title),
                path(RestaurantReview::content),
            ).from(
                entity(RestaurantReview::class),
                innerJoin(RestaurantReview::restaurant),
            ).where(
                path(Restaurant::id).eq(restaurantId),
            ).orderBy(
                path(RestaurantReview::id).desc(),
            )
        }

        entityManager
            .createQuery(query, jpqlRenderContext)
            .apply { maxResults = limit }
            .resultList
    }

    override fun findRestaurantRatingAverages(restaurantId: Long): Either<DBFailure, RestaurantRatingAverageDto> =
        Either.catchDB {
            val query = jpql {
                selectNew<RestaurantRatingAverageDto>(
                    avg(path(RestaurantReview::rating)(RestaurantRating::taste)),
                    avg(path(RestaurantReview::rating)(RestaurantRating::parkingSpace)),
                    avg(path(RestaurantReview::rating)(RestaurantRating::kind)),
                    avg(path(RestaurantReview::rating)(RestaurantRating::clean)),
                    avg(path(RestaurantReview::rating)(RestaurantRating::mood)),
                    count(RestaurantReview::id),
                ).from(
                    entity(RestaurantReview::class),
                    innerJoin(RestaurantReview::restaurant),
                ).where(
                    path(Restaurant::id).eq(restaurantId),
                )
            }

            entityManager
                .createQuery(query, jpqlRenderContext)
                .resultList
                .firstOrNull()
                ?: RestaurantRatingAverageDto.empty
        }
}
