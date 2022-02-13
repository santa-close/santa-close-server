package com.santaclose.app.auth.service

import arrow.core.Either
import arrow.core.Either.Companion.catch
import arrow.core.computations.either
import com.santaclose.app.appUser.repository.AppUserAppQueryRepository
import com.santaclose.app.appUser.repository.AppUserAppRepository
import com.santaclose.app.auth.context.AppSession
import com.santaclose.app.config.KakaoConfig
import com.santaclose.lib.auth.Profile
import com.santaclose.lib.auth.kakao.KakaoAuth
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.logger.logger
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class AuthAppService(
    private val appUserAppQueryRepository: AppUserAppQueryRepository,
    private val appUserAppRepository: AppUserAppRepository,
    kakaoConfig: KakaoConfig,
    builder: WebClient.Builder,
) {
    private val kakaoAuth = KakaoAuth(builder, kakaoConfig.clientId, kakaoConfig.redirectUri)
    private val logger = logger()

    suspend fun signIn(code: String) = either<Throwable, AppSession> {
        val profile = kakaoAuth.getProfile(code).bind()
        val appUser = appUserAppQueryRepository.findBySocialId(profile.id).bind()
            ?: signUp(profile).bind()

        AppSession(appUser.id, appUser.role)
    }.tapLeft { logger.error(it.message, it) }

    suspend fun signUp(profile: Profile): Either<Throwable, AppUser> = catch {
        val appUser = AppUser.signUp(profile.name, profile.email, profile.id)

        appUserAppRepository.save(appUser)
    }
}
