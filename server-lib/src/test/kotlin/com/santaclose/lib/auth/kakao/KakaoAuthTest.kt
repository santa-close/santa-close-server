package com.santaclose.lib.auth.kakao

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.santaclose.lib.auth.Profile
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.string.shouldContain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

internal class KakaoAuthTest : FreeSpec(
    {
        lateinit var kakaoAuth: KakaoAuth
        val server = MockWebServer()

        beforeSpec {
            server.start()
            kakaoAuth =
                KakaoAuth(
                    clientId = "clientId",
                    redirectUri = "http://localhost:8080",
                    tokenUri = "http://localhost:${server.port}",
                    userUri = "http://localhost:${server.port}",
                )
        }

        afterSpec {
            server.shutdown()
        }

        "GetAccessToken" - {
            "토큰 발급에 실패한 경우 에러가 발생한다" {
                // given
                server.enqueue(MockResponse().setResponseCode(500))

                // when
                val result = kakaoAuth.getAccessToken("code")

                // then
                result.shouldBeLeft().apply { message shouldContain "Server Error" }
            }

            "토큰 요청의 응답이 올바르지 않으면 에러가 발생한다" {
                // given
                server.enqueue(
                    MockResponse()
                        .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .setBody("{\"foo\":\"bar\"}"),
                )

                // when
                val result = kakaoAuth.getAccessToken("code")

                // then
                result.shouldBeLeft().apply { message shouldContain "value failed for JSON property" }
            }

            "응답이 올바르면 토큰을 반환한다" {
                // given
                val token = "1234abcd"
                server.enqueue(
                    MockResponse()
                        .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .setBody("{\"access_token\":\"$token\"}"),
                )

                // when
                val result = kakaoAuth.getAccessToken("code")

                // then
                result shouldBeRight KakaoTokenResponse(token)
            }
        }

        "GetUser" - {
            "사용자 조회에 실패한 경우 에러가 발생한다" {
                // given
                server.enqueue(MockResponse().setResponseCode(500))

                // when
                val result = kakaoAuth.getUser("token")

                // then
                result.shouldBeLeft().apply { message shouldContain "Server Error" }
            }

            "사용자 요청의 응답이 올바르지 않으면 에러가 발생한다" {
                // given
                server.enqueue(
                    MockResponse()
                        .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .setBody("{\"id\":123}"),
                )

                // when
                val result = kakaoAuth.getUser("token")

                // then
                result.shouldBeLeft().apply { message shouldContain "value failed for JSON property" }
            }

            "응답이 올바르면 사용자 정보를 반환한다" {
                // given
                val user = KakaoUserResponse(123, KakaoAccountResponse(KakaoProfileResponse("name"), "email"))
                server.enqueue(
                    MockResponse()
                        .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .setBody(jacksonObjectMapper().writeValueAsString(user)),
                )

                // when
                val result = kakaoAuth.getUser("code")

                // then
                result shouldBeRight user
            }
        }

        "GetProfile" - {
            "성공적으로 프로필을 가져온다" {
                // given
                server.enqueue(
                    MockResponse()
                        .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .setBody("{\"access_token\":\"token\"}"),
                )
                server.enqueue(
                    MockResponse()
                        .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .setBody(
                            jacksonObjectMapper()
                                .writeValueAsString(
                                    KakaoUserResponse(123, KakaoAccountResponse(KakaoProfileResponse("name"), "email")),
                                ),
                        ),
                )

                // when
                val result = kakaoAuth.getProfile("code")

                // then
                result shouldBeRight Profile("123", "name", "email")
            }
        }
    },
)
