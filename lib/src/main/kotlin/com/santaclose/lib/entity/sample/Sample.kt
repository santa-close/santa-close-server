package com.santaclose.lib.entity.sample

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.sample.type.SampleStatus
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Entity
class Sample(
    @Column(columnDefinition = "TEXT")
    @field:NotNull
    var name: String,

    @field:Positive
    var price: Int,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @field:NotNull
    var status: SampleStatus,
) : BaseEntity()
