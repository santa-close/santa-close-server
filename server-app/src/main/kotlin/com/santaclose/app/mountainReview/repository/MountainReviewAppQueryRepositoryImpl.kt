package com.santaclose.app.mountainReview.repository

import arrow.core.Either
import arrow.core.recover
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.expression.avg
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.santaclose.app.mountainReview.repository.dto.MountainRatingAverageDto
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainReview.MountainRating
import com.santaclose.lib.entity.mountainReview.MountainReview
import com.santaclose.lib.web.exception.DomainError.DBFailure
import com.santaclose.lib.web.exception.catchDB
import jakarta.persistence.PersistenceException
import jakarta.persistence.criteria.JoinType
import org.springframework.stereotype.Repository

@Repository
class MountainReviewAppQueryRepositoryImpl(
    private val springDataQueryFactory: SpringDataQueryFactory,
) : MountainReviewAppQueryRepository {

    override fun findAllByMountainId(mountainId: Long, limit: Int): Either<DBFailure, List<MountainReview>> =
        Either.catchDB {
            springDataQueryFactory.listQuery {
                val mountainReview: EntitySpec<MountainReview> = entity(MountainReview::class)
                select(mountainReview)
                from(mountainReview)
                fetch(MountainReview::mountain, JoinType.INNER)
                where(col(Mountain::id).equal(mountainId))
                orderBy(col(Mountain::id).desc())
                limit(limit)
            }
        }

    override fun findMountainRatingAverages(mountainId: Long): Either<DBFailure, MountainRatingAverageDto> =
        Either.catch<MountainRatingAverageDto> {
            springDataQueryFactory.singleQuery {
                val mountainReview: EntitySpec<MountainReview> = entity(MountainReview::class)
                selectMulti(
                    avg(MountainRating::scenery),
                    avg(MountainRating::tree),
                    avg(MountainRating::trail),
                    avg(MountainRating::parking),
                    avg(MountainRating::toilet),
                    avg(MountainRating::traffic),
                    count(col(MountainReview::id)),
                )
                associate(MountainReview::class, MountainRating::class, on(MountainReview::rating))
                from(mountainReview)
                join(MountainReview::mountain, JoinType.INNER)
                where(col(Mountain::id).equal(mountainId))
            }
        }.recover {
            if (it is PersistenceException) {
                MountainRatingAverageDto.empty
            } else {
                raise(DBFailure(it))
            }
        }
}
