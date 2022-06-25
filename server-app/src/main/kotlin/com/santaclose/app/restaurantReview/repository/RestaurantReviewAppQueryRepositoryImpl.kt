package com.santaclose.app.restaurantReview.repository

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
import org.springframework.stereotype.Repository
import javax.persistence.criteria.JoinType

@Repository
class RestaurantReviewAppQueryRepositoryImpl(
    private val springDataQueryFactory: SpringDataQueryFactory,
) : RestaurantReviewAppQueryRepository {

    override fun findAllByRestaurant(
        restaurantId: Long,
        limit: Int,
    ): List<LatestRestaurantReviewDto> =
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

    override fun findRestaurantRatingAverages(restaurantId: Long): RestaurantRatingAverageDto =
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
}
