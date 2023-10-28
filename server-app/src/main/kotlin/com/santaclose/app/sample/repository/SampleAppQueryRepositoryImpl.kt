package com.santaclose.app.sample.repository

import arrow.core.Either
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import com.santaclose.app.sample.controller.dto.SampleAppDetail
import com.santaclose.lib.entity.sample.Sample
import com.santaclose.lib.web.exception.DomainError.DBFailure
import com.santaclose.lib.web.exception.catchDB
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class SampleAppQueryRepositoryImpl(
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) : SampleAppQueryRepository {

    override fun findByPrice(price: Int): Either<DBFailure, SampleAppDetail> = Either.catchDB {
        val query = jpql {
            selectNew<SampleAppDetail>(
                path(Sample::name),
                path(Sample::price),
                path(Sample::status),
            ).from(
                entity(Sample::class),
            ).where(
                path(Sample::price).eq(price),
            )
        }

        entityManager.createQuery(query, jpqlRenderContext).singleResult
    }
}
