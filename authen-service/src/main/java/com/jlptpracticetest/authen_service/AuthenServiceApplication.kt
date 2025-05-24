package com.jlptpracticetest.authen_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties::class)
open class AuthenServiceApplication

fun main(args: Array<String>) {
    runApplication<AuthenServiceApplication>(*args)
}
