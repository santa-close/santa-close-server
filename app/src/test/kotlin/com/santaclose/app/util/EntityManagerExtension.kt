package com.santaclose.app.util

import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.appUser.type.AppUserRole
import javax.persistence.EntityManager

fun EntityManager.createAppUser(user: AppUser? = null): AppUser =
    (user ?: AppUser("name", "email", "socialId", AppUserRole.USER))
        .also { this.persist(it) }
