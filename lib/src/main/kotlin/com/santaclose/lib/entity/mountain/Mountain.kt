package com.santaclose.lib.entity.mountain

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.location.Location
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
  @ManyToOne(fetch = LAZY) @field:NotNull var appUser: AppUser,
  @OneToOne(fetch = LAZY) var location: Location,
) : BaseEntity()
