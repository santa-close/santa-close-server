package com.santaclose.lib.entity.appUser

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.appUser.type.AppUserRole
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull

@Entity
class AppUser(
    @Column(length = 15)
    @field:NotNull
    var phoneNumber: String,

    @field:NotNull
    var socialId: String,

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    @field:NotNull
    var role: AppUserRole,
) : BaseEntity() {
    fun hasRole(role: AppUserRole) = this.role == role
}
