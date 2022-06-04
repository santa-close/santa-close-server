package com.santaclose.app.category.resolver

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import com.santaclose.app.category.resolver.dto.CategoryAppList
import org.springframework.stereotype.Component

@Component
class CategoryAppQueryResolver : Query {
    @GraphQLDescription("category 데이터") fun categories() = CategoryAppList()
}
