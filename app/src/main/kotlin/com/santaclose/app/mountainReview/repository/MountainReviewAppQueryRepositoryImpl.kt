package com.santaclose.app.mountainReview.repository

import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.santaclose.lib.entity.mountain.Mountain
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
}
