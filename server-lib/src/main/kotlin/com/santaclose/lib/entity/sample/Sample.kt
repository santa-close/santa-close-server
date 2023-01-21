package com.santaclose.lib.entity.sample

import com.santaclose.lib.entity.BaseEntity
import com.santaclose.lib.entity.sample.type.SampleStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

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
