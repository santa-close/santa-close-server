package com.santaclose.lib.entity.mountain

import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.location.Location
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.ManyToOne
import javax.validation.constraints.NotNull
import org.locationtech.jts.geom.Point

@Entity
class Mountain(
  @field:NotNull var name: String,
  @Column(columnDefinition = "TEXT") @field:NotNull var detail: String,
  @ManyToOne(fetch = LAZY) @field:NotNull var appUser: AppUser,
  point: Point,
) : Location(point)
