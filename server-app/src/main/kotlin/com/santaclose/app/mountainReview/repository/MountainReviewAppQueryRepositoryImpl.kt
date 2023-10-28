package com.santaclose.app.mountainReview.repository

import arrow.core.Either
import arrow.core.recover
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import com.santaclose.app.mountainReview.repository.dto.MountainRatingAverageDto
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainReview.MountainRating
import com.santaclose.lib.entity.mountainReview.MountainReview
import com.santaclose.lib.web.exception.DomainError.DBFailure
import com.santaclose.lib.web.exception.catchDB
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceException
import org.springframework.stereotype.Repository

@Repository
class MountainReviewAppQueryRepositoryImpl(
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) : MountainReviewAppQueryRepository {

    override fun findAllByMountainId(mountainId: Long, limit: Int): Either<DBFailure, List<MountainReview>> =
        Either.catchDB {
            val query = jpql {
                select(
                    entity(MountainReview::class),
                ).from(
                    entity(MountainReview::class),
                    innerJoin(MountainReview::mountain),
                ).where(
                    path(Mountain::id).eq(mountainId),
                ).orderBy(
                    path(Mountain::id).desc(),
                )
            }

            entityManager
                .createQuery(query, jpqlRenderContext)
                .apply { maxResults = limit }
                .resultList
        }

    override fun findMountainRatingAverages(mountainId: Long): Either<DBFailure, MountainRatingAverageDto> =
        Either.catch<MountainRatingAverageDto> {
            val query = jpql {
                selectNew<MountainRatingAverageDto>(
                    avg(path(MountainReview::rating)(MountainRating::scenery)),
                    avg(path(MountainReview::rating)(MountainRating::tree)),
                    avg(path(MountainReview::rating)(MountainRating::trail)),
                    avg(path(MountainReview::rating)(MountainRating::parking)),
                    avg(path(MountainReview::rating)(MountainRating::toilet)),
                    avg(path(MountainReview::rating)(MountainRating::traffic)),
                    count(MountainReview::id),
                ).from(
                    entity(MountainReview::class),
                    innerJoin(MountainReview::mountain),
                ).where(
                    path(Mountain::id).eq(mountainId),
                )
            }

            entityManager
                .createQuery(query, jpqlRenderContext)
                .singleResult
        }.recover {
            if (it is PersistenceException) {
                MountainRatingAverageDto.empty
            } else {
                raise(DBFailure(it))
            }
        }
}
