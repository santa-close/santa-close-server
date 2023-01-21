package com.santaclose.lib.entity.appUser

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.appUser.type.AppUserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class AppUser(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var email: String,

    @Column(unique = true, nullable = false)
    var socialId: String,

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    var role: AppUserRole,
) : BaseEntity() {
    companion object {
        fun signUp(name: String, email: String, socialId: String) =
            AppUser(name, email, socialId, AppUserRole.USER)
    }
}
