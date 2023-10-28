package com.santaclose.app.appUser.repository

import arrow.core.Either
import arrow.core.Either.Companion.catch
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import com.santaclose.lib.entity.appUser.AppUser
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class AppUserAppQueryRepositoryImpl(
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) : AppUserAppQueryRepository {
    override fun findBySocialId(socialId: String): Either<Throwable, AppUser?> = catch {
        val query = jpql {
            select(
                entity(AppUser::class),
            ).from(
                entity(AppUser::class),
            ).where(
                path(AppUser::socialId).eq(socialId),
            )
        }

        entityManager
            .createQuery(query, jpqlRenderContext)
            .apply { maxResults = 1 }
            .resultList
            .firstOrNull()
    }
}
