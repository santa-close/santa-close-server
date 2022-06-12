package com.santaclose.app.mountain.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import com.santaclose.lib.entity.mountain.Mountain
import org.springframework.stereotype.Repository
import javax.persistence.criteria.JoinType

@Repository
class MountainAppQueryRepositoryImpl(
    private val springDataQueryFactory: SpringDataQueryFactory,
) : MountainAppQueryRepository {

    override fun findOneWithLocation(id: Long): Mountain =
        springDataQueryFactory.singleQuery {
            select(entity(Mountain::class))
            from(Mountain::class)
            fetch(Mountain::location, JoinType.INNER)
            where(col(Mountain::id).equal(id))
        }
}
