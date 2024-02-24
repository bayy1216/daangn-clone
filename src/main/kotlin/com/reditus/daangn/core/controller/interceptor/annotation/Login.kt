package com.reditus.daangn.core.controller.interceptor.annotation

import io.swagger.v3.oas.annotations.Hidden

/**
 * 로그인이 필요한 API에 사용하는 어노테이션
 * [UserAuth]와 함께사용
 */
@Hidden // swagger 문서에 표시하지 않음
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Login()
