package com.reditus.daangn.core.aop

/**
 * 임시용 API
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TemporaryApi(
    val reason: String = "임시용 API"
)