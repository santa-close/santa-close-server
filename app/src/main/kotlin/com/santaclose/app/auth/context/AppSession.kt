package com.santaclose.app.auth.context

import com.santaclose.lib.entity.appUser.type.AppUserRole

data class AppSession(val id: Long, val role: AppUserRole)
