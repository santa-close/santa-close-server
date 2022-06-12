package com.santaclose.app.mountain.resolver.dto

import org.springframework.format.annotation.NumberFormat

data class MountainDetailAppInput(
    @NumberFormat(style = NumberFormat.Style.NUMBER) val id: String,
)