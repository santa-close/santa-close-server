package com.santaclose.app.auth.service

import arrow.core.Either
import arrow.core.Either.Companion.catch
import arrow.core.continuations.either
import com.santaclose.app.appUser.repository.AppUserAppQueryRepository
import com.santaclose.app.appUser.repository.AppUserAppRepository
import com.santaclose.app.auth.security.AppSession
import com.santaclose.lib.auth.Profile
import com.santaclose.lib.entity.appUser.AppUser
import org.springframework.stereotype.Service

@Service
class AuthAppService(
    private val appUserAppQueryRepository: AppUserAppQueryRepository,
    private val appUserAppRepository: AppUserAppRepository,
    private val authManager: AuthManager,
) {

    fun signIn(code: String) =
        either.eager {
            val profile = authManager.getProfile(code).bind()
            val appUser =
                appUserAppQueryRepository.findBySocialId(profile.id).bind() ?: createAppUser(profile).bind()

            AppSession(appUser.id, appUser.role)
        }

    private fun createAppUser(profile: Profile): Either<Throwable, AppUser> = catch {
        val appUser = AppUser.signUp(profile.name, profile.email, profile.id)

        appUserAppRepository.save(appUser)
    }
}
