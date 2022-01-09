package com.santaclose.lib.extension

import arrow.core.None
import arrow.core.Option
import com.querydsl.core.NonUniqueResultException
import com.querydsl.jpa.JPQLQuery

fun <T> JPQLQuery<T>.fetchOption(): Option<T> = try {
    fetchOne().let { Option.fromNullable(it) }
} catch (e: NonUniqueResultException) {
    None
}
