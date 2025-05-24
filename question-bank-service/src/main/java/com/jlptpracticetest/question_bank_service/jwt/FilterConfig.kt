package com.jlptpracticetest.question_bank_service.jwt

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class FilterConfig(val jwtFilter: JwtFilter) {

    @Bean
    open fun filterRegistrationBean(): FilterRegistrationBean<JwtFilter> {
        val registration = FilterRegistrationBean<JwtFilter>()
        registration.filter = jwtFilter
        registration.addUrlPatterns("/api/question/*") // hoặc /api/question/*
        return registration
    }
}
