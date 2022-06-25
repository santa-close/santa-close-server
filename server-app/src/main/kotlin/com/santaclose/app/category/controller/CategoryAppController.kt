package com.santaclose.app.category.controller

import com.santaclose.app.category.controller.dto.CategoryAppList
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class CategoryAppController {

    @QueryMapping
    fun categories(): CategoryAppList = CategoryAppList()
}
