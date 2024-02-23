package com.reditus.daangn.core.controller.interceptor

import com.reditus.daangn.core.controller.interceptor.annotation.Admin
import com.reditus.daangn.core.controller.interceptor.annotation.JwtFilterExclusion
import com.reditus.daangn.core.domain.MemberType
import com.reditus.daangn.core.jwt.JwtProvider
import com.reditus.daangn.core.utils.DataUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import kotlin.reflect.KClass

@Component
class JwtInterceptor(
    private val jwtProvider: JwtProvider,
    private val dataUtils: DataUtils,
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        /**
         * [JwtFilterExclusion] 어노테이션이 붙어있는 경우에는 통과
         */
        val checkJwtExclusion = checkAnnotation(handler, JwtFilterExclusion::class)
        if(checkJwtExclusion) return true


        /**
         * [Jwt] 토큰 유효성 검사후 memberAuth를 추출
         */
        val rawString = request.getHeader("Authorization") ?: throw IllegalArgumentException("Authorization 헤더가 존재하지 않습니다.")
        val token = dataUtils.extractAuthorizationHeader(header= rawString, isLogin = false)
        if(!jwtProvider.validateToken(token)){
            throw IllegalArgumentException("토큰이 유효하지 않습니다.")
        }
        val memberAuth = jwtProvider.extractMemberAuth(token)


        /**
         * [Admin] 권한 체크 user의 권한이 [UserType.ADMIN]인 경우에만 통과
         */
        val checkAdmin = checkAnnotation(handler, Admin::class)
        if (checkAdmin) {
            if (memberAuth.type != MemberType.ADMIN) {
                throw IllegalArgumentException("권한이 없습니다.")
            }
            return true
        }

        /**
         * memberAuth를 [request]에 저장
         */
        request.setAttribute("memberAuth", memberAuth)
        return true
    }

    private fun checkAnnotation(handler:Any, clazz: KClass<out Annotation>) : Boolean{
        val handlerMethod = handler as? HandlerMethod
        return handlerMethod?.method?.getAnnotation(clazz.java) != null
    }
}