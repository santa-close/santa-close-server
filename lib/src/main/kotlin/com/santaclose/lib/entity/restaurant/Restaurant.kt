package com.santaclose.lib.entity.restaurant

import com.santaclose.lib.converter.StringListConverter
import com.santaclose.lib.entity.appUser.AppUser
import com.santaclose.lib.entity.location.Location
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.ManyToOne
import javax.validation.constraints.NotNull
import org.locationtech.jts.geom.Point

@Entity
class Restaurant(
  @field:NotNull var name: String,
  @Column(length = 100) var description: String,
  @Convert(converter = StringListConverter::class)
  var images: MutableList<String> = mutableListOf(),
  @ManyToOne(fetch = LAZY) @field:NotNull var appUser: AppUser,
  point: Point,
) : Location(point)
