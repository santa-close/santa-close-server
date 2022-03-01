package com.santaclose.app.appUser.repository

import com.santaclose.lib.entity.appUser.AppUser
import org.springframework.data.jpa.repository.JpaRepository

interface AppUserAppRepository : JpaRepository<AppUser, Long>
