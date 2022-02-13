package com.santaclose.app.appUser.repository

import arrow.core.Either
import arrow.core.Either.Companion.catch
import arrow.core.Option
import arrow.core.firstOrNone
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.santaclose.lib.entity.appUser.AppUser
import org.springframework.stereotype.Repository

@Repository
class AppUserAppQueryRepositoryImpl(
    private val springDataQueryFactory: SpringDataQueryFactory,
) : AppUserAppQueryRepository {
    override fun findBySocialId(socialId: String): Either<Throwable, Option<AppUser>> = catch {
        springDataQueryFactory.listQuery<AppUser> {
            select(entity(AppUser::class))
            from(AppUser::class)
            where(col(AppUser::socialId).equal(socialId))
            limit(1)
        }.firstOrNone()
    }
}
