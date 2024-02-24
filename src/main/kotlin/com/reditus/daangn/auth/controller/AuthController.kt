package com.reditus.daangn.auth.controller

import com.reditus.daangn.auth.service.AuthService
import com.reditus.daangn.core.controller.interceptor.annotation.JwtFilterExclusion
import com.reditus.daangn.core.jwt.JwtToken
import com.reditus.daangn.core.utils.DataUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val dataUtils: DataUtils,
) {

    @JwtFilterExclusion
    @PostMapping("/login")
    fun login(@RequestHeader("Authorization") rawHeader: String) :JwtToken{
        val rawString = dataUtils.extractAuthorizationHeader(rawHeader, true)
        val (email, password) = String(dataUtils.decodeBase64(rawString)).split(":")
        return authService.login(email, password)
    }

    @JwtFilterExclusion
    @PostMapping("/refresh")
    fun refresh(@RequestHeader("Authorization") rawHeader: String) :JwtToken{
        val token = dataUtils.extractAuthorizationHeader(rawHeader, false)
        return authService.refresh(token)
    }
}