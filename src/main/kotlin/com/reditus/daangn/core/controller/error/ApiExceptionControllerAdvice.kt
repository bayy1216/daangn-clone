package com.reditus.daangn.core.controller.error

import io.jsonwebtoken.JwtException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionControllerAdvice {
    private val log = LoggerFactory.getLogger(this::class.java)
    @ExceptionHandler
    fun illegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        log.info("IllegalArgumentException", e)
        return ResponseEntity(
            ErrorResponse(
                code = "COMMON-ILLEGAL-ARGUMENT-EXCEPTION",
                message = e.message ?: "COMMON-ILLEGAL-ARGUMENT-EXCEPTION",
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler
    fun illegalStateException(e: IllegalStateException): ResponseEntity<ErrorResponse> {
        log.error("IllegalStateException", e)
        return ResponseEntity(
            ErrorResponse(
                code = "COMMON-ILLEGAL-STATE-EXCEPTION",
                message = e.message ?: "COMMON-ILLEGAL-STATE-EXCEPTION",
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler
    fun jwtException(e: JwtException): ResponseEntity<ErrorResponse> {
        log.debug("JwtException", e)
        return ResponseEntity(
            ErrorResponse(
                code = "JWT-EXCEPTION",
                message = e.message ?: "JWT-EXCEPTION",
            ),
            HttpStatus.UNAUTHORIZED
        )
    }
    @ExceptionHandler
    fun runtimeException(e: RuntimeException): ResponseEntity<ErrorResponse> {
        log.error("RuntimeException", e)
        return ResponseEntity(
            ErrorResponse(
                code = "COMMON-RUNTIME-EXCEPTION",
                message = e.message ?: "COMMON-RUNTIME-EXCEPTION",
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}