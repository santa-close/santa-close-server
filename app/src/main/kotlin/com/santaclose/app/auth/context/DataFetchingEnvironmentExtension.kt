package com.santaclose.app.auth.context

import arrow.core.Option
import com.santaclose.lib.entity.appUser.AppUser
import graphql.schema.DataFetchingEnvironment

fun DataFetchingEnvironment.user(): Option<AppUser> = this.graphQlContext.get("user")
