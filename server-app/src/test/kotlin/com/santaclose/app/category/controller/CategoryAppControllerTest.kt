package com.santaclose.app.category.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.santaclose.app.category.controller.dto.CategoryAppList
import io.kotest.core.spec.style.FreeSpec
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.HttpGraphQlTester
import java.io.File

@SpringBootTest
@AutoConfigureHttpGraphQlTester
internal class CategoryAppControllerTest(
    private val graphQlTester: HttpGraphQlTester,
) : FreeSpec({

    "categories" - {
        "요청한 category 정보를 file 에 저장한다" {
            // when
            val response = graphQlTester
                .documentName("categories")
                .execute()

            // then
            response
                .path("categories")
                .entity(CategoryAppList::class.java)
                .satisfies {
                    val result = mapOf("data" to mapOf("categories" to it))
                    val body = ObjectMapper()
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(result)

                    File("src/main/resources/graphql/categories.json").writeText(body)
                }
        }
    }
})
