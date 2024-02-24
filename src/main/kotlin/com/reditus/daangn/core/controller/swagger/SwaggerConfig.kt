package com.reditus.daangn.core.controller.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(components())
        .info(apiInfo())

    private fun components() = Components()
        .addSecuritySchemes("bearer-key", bearerJwtSecurityScheme())


    /**
     * Bearer JWT Security Scheme
     * 컨트롤러에서 사용하려면 @SecurityRequirement(name = "bearer-key") 를 사용해야함
     */
    private fun bearerJwtSecurityScheme() = SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .description("JWT 인증을 위한 토큰을 입력하세요. (예: Bearer {token}에서 token만 입력)")

    private fun apiInfo() = Info()
        .title("당근마켓 클론코딩 API")
        .description("당근마켓 클론코딩 API 명세서입니다.")
        .version("0.0.1")
}