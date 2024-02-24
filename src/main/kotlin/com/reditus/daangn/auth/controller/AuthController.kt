package com.reditus.daangn.auth.controller

import com.reditus.daangn.auth.service.AuthService
import com.reditus.daangn.core.controller.interceptor.annotation.JwtFilterExclusion
import com.reditus.daangn.core.jwt.JwtToken
import com.reditus.daangn.core.utils.DataUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Auth", description = "로그인, 토큰 갱신")
@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val dataUtils: DataUtils,
) {

    @Operation(
        summary = "로그인", description = "이메일,패스워드로 base64 인코딩하여 로그인요청.",
        responses = [
            ApiResponse(responseCode = "201", description = "로그인 성공"),
        ]
    )
    @JwtFilterExclusion
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    fun login(@RequestHeader("Authorization") rawHeader: String) :JwtToken{
        val rawString = dataUtils.extractAuthorizationHeader(rawHeader, true)
        val (email, password) = String(dataUtils.decodeBase64(rawString)).split(":")
        return authService.login(email, password)
    }


    @Operation(
        summary = "토큰 갱신", description = "리프레시 토큰을 이용하여 토큰을 갱신합니다.",
        responses = [
            ApiResponse(responseCode = "201", description = "토큰 갱신 성공"),
        ]
    )
    @JwtFilterExclusion
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    fun refresh(@RequestHeader("Authorization") rawHeader: String) :JwtToken{
        val token = dataUtils.extractAuthorizationHeader(rawHeader, false)
        return authService.refresh(token)
    }
}