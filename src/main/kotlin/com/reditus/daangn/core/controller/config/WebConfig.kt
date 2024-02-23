package com.reditus.daangn.core.controller.config

import com.reditus.daangn.core.controller.interceptor.JwtInterceptor
import com.reditus.daangn.core.controller.interceptor.JwtLoginResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val jwtInterceptor: JwtInterceptor,
    private val jwtLoginResolver: JwtLoginResolver
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(jwtInterceptor)
            .order(1)
            .addPathPatterns("/api/**")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(jwtLoginResolver)
    }
}