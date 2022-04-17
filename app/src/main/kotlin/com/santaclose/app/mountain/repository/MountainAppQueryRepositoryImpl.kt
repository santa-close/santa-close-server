package com.santaclose.app.mountain.repository

import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.santaclose.lib.entity.mountain.Mountain
import javax.persistence.criteria.JoinType
import org.springframework.stereotype.Repository

@Repository
class MountainAppQueryRepositoryImpl(
  private val springDataQueryFactory: SpringDataQueryFactory,
) : MountainAppQueryRepository {

  override fun findOneWithLocation(id: Long): Mountain =
    springDataQueryFactory.singleQuery {
      select(entity(Mountain::class))
      from(Mountain::class)
      join(Mountain::location, JoinType.INNER)
    }
}
