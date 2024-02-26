package com.reditus.daangn.core.controller.error

import com.reditus.daangn.core.exception.ResourceNotFoundException
import io.jsonwebtoken.JwtException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionControllerAdvice {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun illegalArgumentException(e: IllegalArgumentException): ErrorResponse {
        log.info("IllegalArgumentException", e)
        return ErrorResponse(
            code = "COMMON-ILLEGAL-ARGUMENT-EXCEPTION",
            message = e.message ?: "COMMON-ILLEGAL-ARGUMENT-EXCEPTION",
        )
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun illegalStateException(e: IllegalStateException): ErrorResponse {
        log.error("IllegalStateException", e)
        return ErrorResponse(
            code = "COMMON-ILLEGAL-STATE-EXCEPTION",
            message = e.message ?: "COMMON-ILLEGAL-STATE-EXCEPTION",
        )
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun jwtException(e: JwtException): ErrorResponse {
        log.debug("JwtException", e)
        return ErrorResponse(
            code = "JWT-EXCEPTION",
            message = e.message ?: "JWT-EXCEPTION",
        )
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun resourceNotFoundException(e: ResourceNotFoundException): ErrorResponse {
        log.error("ResourceNotFoundException", e)
        return ErrorResponse(
            code = "RESOURCE-NOT-FOUND-EXCEPTION",
            message = e.message ?: "RESOURCE-NOT-FOUND-EXCEPTION",
        )
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun runtimeException(e: RuntimeException): ErrorResponse {
        log.error("RuntimeException", e)
        return ErrorResponse(
            code = "COMMON-RUNTIME-EXCEPTION",
            message = e.message ?: "COMMON-RUNTIME-EXCEPTION",
        )
    }
}