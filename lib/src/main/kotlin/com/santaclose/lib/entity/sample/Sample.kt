package com.santaclose.lib.entity.sample

import com.santaclose.lib.entity.BaseEntity
import javax.persistence.Entity

@Entity
class Sample(
    var name: String,
    var price: Int
) : BaseEntity()
