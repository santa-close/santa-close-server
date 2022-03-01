package com.santaclose.app.appUser.repository

import arrow.core.Either
import com.santaclose.lib.entity.appUser.AppUser

interface AppUserAppQueryRepository {
    fun findBySocialId(socialId: String): Either<Throwable, AppUser?>
}
