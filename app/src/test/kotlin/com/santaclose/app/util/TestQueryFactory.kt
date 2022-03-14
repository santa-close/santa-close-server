package com.santaclose.app.util

import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreatorImpl
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactoryImpl
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

internal abstract class TestQueryFactory {
  @PersistenceContext protected lateinit var em: EntityManager

  protected val queryFactory: SpringDataQueryFactory by lazy {
    SpringDataQueryFactoryImpl(
      criteriaQueryCreator = CriteriaQueryCreatorImpl(em),
      subqueryCreator = SubqueryCreatorImpl()
    )
  }
}
