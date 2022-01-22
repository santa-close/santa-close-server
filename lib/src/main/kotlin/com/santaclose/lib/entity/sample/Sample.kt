package com.santaclose.lib.entity.sample

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.sample.type.SampleStatus
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.Positive

@Entity
class Sample(
    var name: String,
    @field:Positive
    var price: Int,
    @Enumerated(EnumType.STRING)
    var status: SampleStatus,
) : BaseEntity()
