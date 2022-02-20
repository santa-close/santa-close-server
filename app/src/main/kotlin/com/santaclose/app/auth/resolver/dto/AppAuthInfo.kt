package com.santaclose.app.auth.resolver.dto

import com.santaclose.app.auth.context.AppSession

data class AppAuthInfo(
    val accessToken: String,
    val expiredAt: String,
) {
    companion object {
        fun by(appSession: AppSession) = AppAuthInfo("token", "expired")
    }
}
