package com.santaclose.lib.entity.sample

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.sample.type.SampleStatus
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Sample(
    var name: String,
    var price: Int,
    @Enumerated(EnumType.STRING)
    var status: SampleStatus,
) : BaseEntity()
