package com.santaclose.app.category.resolver

import com.santaclose.app.category.resolver.dto.CategoryAppList
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

// @Component
// class CategoryAppQueryResolver : Query {
//    @GraphQLDescription("category 데이터")
//    fun categories() = CategoryAppList()
// }

@Controller
class CategoryAppController {

    @QueryMapping
    fun categories() = CategoryAppList()
}
