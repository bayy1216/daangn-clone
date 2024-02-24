package com.reditus.daangn.core.controller.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .info(apiInfo())

    private fun apiInfo() = Info()
        .title("당근마켓 클론코딩 API")
        .description("당근마켓 클론코딩 API 명세서입니다.")
        .version("0.0.1")
}