package com.santaclose.lib.entity.mountain

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.coordinate.Coordinates
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.validation.constraints.NotNull

@Entity
class Mountain(
  @field:NotNull var name: String,
  @Column(columnDefinition = "TEXT") @field:NotNull var detail: String,

// 위치 id,

    @OneToOne(fetch = LAZY)
    @field:NotNull
    var coordinates: Coordinates,

    @ManyToOne(fetch = LAZY)
    @field:NotNull
    var appUser: AppUser,
) : BaseEntity()
