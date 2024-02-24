package com.reditus.daangn.core.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class TemporaryApiAdvice {
    private val logger = LoggerFactory.getLogger(this::class.java)
    @Around("@annotation(com.reditus.daangn.core.aop.TemporaryApi)")
    fun temporaryApi(joinPoint: ProceedingJoinPoint): Any? {
        val method = (joinPoint.signature as MethodSignature).method
        val annotation = method.getAnnotation(TemporaryApi::class.java)
        logger.warn("임시용 API 사용됨[${annotation.reason}] : ${joinPoint.signature.name} }")

        val proceed = joinPoint.proceed()

        return proceed
    }

    companion object {

    }
}
