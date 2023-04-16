package com.santaclose.app.restaurantReview.repository

import arrow.core.Either
import arrow.core.recover
import com.linecorp.kotlinjdsl.querydsl.expression.avg
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.count
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.santaclose.app.restaurantReview.repository.dto.LatestRestaurantReviewDto
import com.santaclose.app.restaurantReview.repository.dto.RestaurantRatingAverageDto
import com.santaclose.lib.entity.restaurant.Restaurant
import com.santaclose.lib.entity.restaurantReview.RestaurantRating
import com.santaclose.lib.entity.restaurantReview.RestaurantReview
import com.santaclose.lib.web.exception.DomainError.DBFailure
import com.santaclose.lib.web.exception.catchDB
import jakarta.persistence.PersistenceException
import jakarta.persistence.criteria.JoinType
import org.springframework.stereotype.Repository

@Repository
class RestaurantReviewAppQueryRepositoryImpl(
    private val springDataQueryFactory: SpringDataQueryFactory,
) : RestaurantReviewAppQueryRepository {

    override fun findAllByRestaurant(
        restaurantId: Long,
        limit: Int,
    ): Either<DBFailure, List<LatestRestaurantReviewDto>> = Either.catchDB {
        springDataQueryFactory.listQuery {
            selectMulti(
                col(RestaurantReview::id),
                col(RestaurantReview::title),
                col(RestaurantReview::content),
            )
            from(RestaurantReview::class)
            join(RestaurantReview::restaurant, JoinType.INNER)
            where(col(Restaurant::id).equal(restaurantId))
            orderBy(col(RestaurantReview::id).desc())
            limit(limit)
        }
    }

    override fun findRestaurantRatingAverages(restaurantId: Long): Either<DBFailure, RestaurantRatingAverageDto> =
        Either.catch<RestaurantRatingAverageDto> {
            springDataQueryFactory.singleQuery {
                selectMulti(
                    avg(RestaurantRating::taste),
                    avg(RestaurantRating::parkingSpace),
                    avg(RestaurantRating::kind),
                    avg(RestaurantRating::clean),
                    avg(RestaurantRating::mood),
                    count(RestaurantReview::id),
                )
                from(RestaurantReview::class)
                join(RestaurantReview::restaurant, JoinType.INNER)
                associate(RestaurantReview::class, RestaurantRating::class, on(RestaurantReview::rating))
                where(col(Restaurant::id).equal(restaurantId))
            }
        }.recover {
            if (it is PersistenceException) {
                RestaurantRatingAverageDto.empty
            } else {
                raise(DBFailure(it))
            }
        }
}
