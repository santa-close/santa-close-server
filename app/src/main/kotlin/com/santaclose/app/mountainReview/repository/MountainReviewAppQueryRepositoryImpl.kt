package com.santaclose.app.mountainReview.repository

import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.expression.avg
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.count
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.selectQuery
import com.santaclose.app.mountainReview.repository.dto.MountainRatingAverageDto
import com.santaclose.lib.entity.mountain.Mountain
import com.santaclose.lib.entity.mountainReview.MountainRating
import com.santaclose.lib.entity.mountainReview.MountainReview
import javax.persistence.criteria.JoinType
import org.springframework.stereotype.Repository

@Repository
class MountainReviewAppQueryRepositoryImpl(
  private val springDataQueryFactory: SpringDataQueryFactory,
) : MountainReviewAppQueryRepository {

  override fun findAllByMountainId(mountainId: Long, limit: Int): List<MountainReview> =
    springDataQueryFactory.listQuery {
      val mountainReview: EntitySpec<MountainReview> = entity(MountainReview::class)
      select(mountainReview)
      from(mountainReview)
      join(MountainReview::mountain, JoinType.INNER)
      where(col(Mountain::id).equal(mountainId))
      orderBy(col(Mountain::id).desc())
      limit(limit)
    }

  override fun findMountainRatingAverages(mountainId: Long): MountainRatingAverageDto {
    val selectQuery =
      springDataQueryFactory.selectQuery<MountainRatingAverageDto> {
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
    return selectQuery.singleResult
  }
}
