package com.santaclose.lib.web

import com.expediagroup.graphql.generator.scalars.ID

fun ID.toLong() = value.toLong()

fun Long.toID() = ID(this.toString())
