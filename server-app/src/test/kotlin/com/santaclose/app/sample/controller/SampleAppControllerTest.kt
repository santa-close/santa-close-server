package com.santaclose.app.sample.controller

import arrow.core.left
import arrow.core.right
import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.sample.controller.dto.CreateSampleAppInput
import com.santaclose.app.sample.controller.dto.SampleAppDetail
import com.santaclose.app.sample.controller.dto.SampleAppItemInput
import com.santaclose.app.sample.service.SampleAppMutationService
import com.santaclose.app.sample.service.SampleAppQueryService
import com.santaclose.lib.entity.sample.type.SampleStatus
import io.kotest.core.spec.style.FreeSpec
import io.mockk.every
import io.mockk.justRun
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.HttpGraphQlTester
import javax.persistence.NoResultException

@SpringBootTest
@AutoConfigureHttpGraphQlTester
internal class SampleAppControllerTest(
    private val graphQlTester: HttpGraphQlTester,
    @MockkBean
    private val sampleAppQueryService: SampleAppQueryService,
    @MockkBean
    private val sampleAppMutationService: SampleAppMutationService,
) : FreeSpec({

    "sample" - {
        "데이터가 없는 경우 에러가 발생한다" {
            // given
            val input = SampleAppItemInput(price = 123)
            every { sampleAppQueryService.findByPrice(123) } returns NoResultException("no result").left()

            // when
            val response = graphQlTester
                .documentName("sample")
                .variable("input", input)
                .execute()

            // then
            response
                .errors()
                .expect { it.message == "no result" }
        }

        "데이터가 있는 경우 sample 을 가져온다" {
            // given
            val input = SampleAppItemInput(price = 123)
            val detail = SampleAppDetail("name", 1000, SampleStatus.OPEN)
            every { sampleAppQueryService.findByPrice(123) } returns detail.right()

            // when
            val response = graphQlTester
                .documentName("sample")
                .variable("input", input)
                .execute()

            // then
            response
                .path("sample")
                .entity(SampleAppDetail::class.java)
                .isEqualTo(detail)
        }
    }

    "createSample" - {
        "샘플 생성에 성공한다" {
            // given
            val input = CreateSampleAppInput(name = "name", price = 123, status = SampleStatus.CLOSE)

            justRun { sampleAppMutationService.create(any()) }

            // when
            val response = graphQlTester
                .documentName("createSample")
                .variable("input", input)
                .execute()

            // then
            response
                .path("createSample")
                .entity(Boolean::class.java)
                .isEqualTo(true)
        }
    }
})
