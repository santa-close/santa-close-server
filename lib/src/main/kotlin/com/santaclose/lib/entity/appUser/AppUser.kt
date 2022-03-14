package com.santaclose.lib.entity.appUser

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.appUser.type.AppUserRole
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class AppUser(
  @Column(nullable = false) var name: String,
  @Column(nullable = false) var email: String,
  @Column(unique = true, nullable = false) var socialId: String,
  @Enumerated(EnumType.STRING) @Column(length = 10, nullable = false) var role: AppUserRole,
) : BaseEntity() {
  companion object {
    fun signUp(name: String, email: String, socialId: String) =
      AppUser(name, email, socialId, AppUserRole.USER)
  }
}
