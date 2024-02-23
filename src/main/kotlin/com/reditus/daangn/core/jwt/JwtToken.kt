package com.reditus.daangn.core.jwt

data class JwtToken(
    val accessToken: String,
    val refreshToken: String,
)
