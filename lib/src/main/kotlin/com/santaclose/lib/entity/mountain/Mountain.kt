package com.santaclose.lib.entity.mountain

import com.santaclose.lib.entity.BaseEntity
import javax.persistence.Entity

@Entity
class Mountain(
    var name: String,

    var detail: String,

    // 위치 id,

    // 추가한 사용자 id nullable,
) : BaseEntity()
