package com.santaclose.lib.entity.mountain

import com.santaclose.lib.entity.BaseEntity
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class Mountain(
    @NotNull
    var name: String,

    @NotNull
    var detail: String,

    // 위치 id,

    // 추가한 사용자 id nullable,
) : BaseEntity()
