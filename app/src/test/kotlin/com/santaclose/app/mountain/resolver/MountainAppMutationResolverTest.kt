package com.santaclose.app.mountain.resolver

import com.ninjasquad.springmockk.MockkBean
import com.santaclose.app.mountain.service.MountainAppMutationService
import com.santaclose.app.util.AppContextMocker
import com.santaclose.app.util.GraphqlBody
import com.santaclose.app.util.gqlRequest
import com.santaclose.app.util.withSuccess
import com.santaclose.lib.entity.appUser.type.AppUserRole
import io.mockk.justRun
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
internal class MountainAppMutationResolverTest
@Autowired
constructor(
    private val webTestClient: WebTestClient,
    @MockkBean private val mountainAppMutationService: MountainAppMutationService
) : AppContextMocker() {
    @Nested
    inner class Register {
        @Test
        fun `유저가 산을 등록한다`() {
            // given
            val query =
                GraphqlBody(
                    """mutation {
            | registerMountain(input: { 
            |    name: "name"
            |    images: ["images"] 
            |    management: NATIONAL 
            |    altitude: 100 
            |    longitude: 100 
            |    latitude: 50 
            |    address : "address"
            |    postcode : "postcode"
            |  })
            |}
            """.trimMargin()
                )
            val session = withMockUser(AppUserRole.USER)
            justRun { mountainAppMutationService.register(any(), session.id) }

            // when
            val response = webTestClient.gqlRequest(query)

            // then
            response.withSuccess("registerMountain") {}
        }
    }
}
