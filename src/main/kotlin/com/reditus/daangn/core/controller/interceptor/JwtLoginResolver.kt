package com.reditus.daangn.core.controller.interceptor

import com.reditus.daangn.core.controller.interceptor.annotation.Login
import com.reditus.daangn.core.jwt.MemberAuth
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class JwtLoginResolver: HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(Login::class.java)
                && parameter.parameterType == MemberAuth::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        return webRequest.getAttribute("memberAuth", NativeWebRequest.SCOPE_REQUEST)
    }
}