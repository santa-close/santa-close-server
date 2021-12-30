package com.santaclose.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@EntityScan(basePackages = ["com.santaclose.entity"])
class AppApplication

fun main(args: Array<String>) {
    runApplication<AppApplication>(*args)
}
