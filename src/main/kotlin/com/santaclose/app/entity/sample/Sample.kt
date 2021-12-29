package com.santaclose.app.entity.sample

import com.santaclose.app.entity.BaseEntity
import javax.persistence.Entity

@Entity
class Sample(
    var name: String,
    var price: Int
) : BaseEntity()
