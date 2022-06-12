package com.santaclose.app.auth.security

import com.santaclose.lib.entity.appUser.type.AppUserRole
import org.springframework.security.core.Authentication

val Authentication.id: Long
    get() = principal as Long

val Authentication.role: AppUserRole
    get() = principal as AppUserRole
