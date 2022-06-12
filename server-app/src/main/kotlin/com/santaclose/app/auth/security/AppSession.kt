package com.santaclose.app.auth.security

import com.santaclose.lib.entity.appUser.type.AppUserRole

data class AppSession(val id: Long, val role: AppUserRole)
