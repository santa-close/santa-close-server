package com.santaclose.entity.sample

import com.santaclose.entity.BaseEntity
import javax.persistence.Entity

@Entity
class Sample(
    var name: String,
    var price: Int
) : BaseEntity()
