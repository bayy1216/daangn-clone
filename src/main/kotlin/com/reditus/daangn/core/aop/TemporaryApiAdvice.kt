package com.reditus.daangn.core.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class TemporaryApiAdvice {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @After("@annotation(temporaryApi)")
    fun temporaryApiAfter(joinPoint: JoinPoint, temporaryApi: TemporaryApi) {
        logger.warn("임시용 API 사용됨 [${joinPoint.signature.name}] : ${temporaryApi.reason}")
    }
}

/**
 * 임시용 API
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TemporaryApi(
    val reason: String = "임시용 API"
)