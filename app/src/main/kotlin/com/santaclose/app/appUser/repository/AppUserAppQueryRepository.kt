package com.santaclose.app.appUser.repository

import arrow.core.Either
import arrow.core.Option
import com.santaclose.lib.entity.appUser.AppUser

interface AppUserAppQueryRepository {
    fun findBySocialId(socialId: String): Either<Throwable, Option<AppUser>>
}
