package com.santaclose.entity

import javax.persistence.Entity

@Entity
class Sample(
    var name: String,
    var price: Int
) : BaseEntity()
