package com.santaclose.lib.entity.mountain

import com.santaclose.lib.entity.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class Mountain(
  @field:NotNull var name: String,
  @Column(columnDefinition = "TEXT") @field:NotNull var detail: String,

// 위치 id,

// 추가한 사용자 id nullable,
) : BaseEntity()
