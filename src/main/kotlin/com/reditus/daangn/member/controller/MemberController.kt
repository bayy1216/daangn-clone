package com.reditus.daangn.member.controller

import com.reditus.daangn.auth.service.AuthService
import com.reditus.daangn.core.controller.interceptor.annotation.JwtFilterExclusion
import com.reditus.daangn.core.controller.interceptor.annotation.Login
import com.reditus.daangn.core.jwt.JwtToken
import com.reditus.daangn.core.jwt.MemberAuth
import com.reditus.daangn.member.controller.dto.request.EmailSignupRequest
import com.reditus.daangn.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Member", description = "회원가입, 회원탈퇴")
@RestController
@RequestMapping("api/v1/members")
class MemberController(
    private val memberService: MemberService,
    private val authService: AuthService,
) {

    @Operation(
        summary = "회원가입", description = "이메일, 패스워드로 회원가입을 요청합니다.",
        responses = [
            ApiResponse(responseCode = "201", description = "회원가입 성공"),
        ]
    )
    @JwtFilterExclusion
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMember(@Valid @RequestBody request: EmailSignupRequest) : JwtToken{
        memberService.createMember(request)
        return authService.login(request.email, request.password)
    }

    @Operation(
        summary = "회원탈퇴", description = "회원탈퇴를 요청합니다.",
        responses = [
            ApiResponse(responseCode = "204", description = "회원탈퇴 성공"),
        ]
    )
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMember(@Login memberAuth: MemberAuth){
        memberService.deleteMember(memberAuth.id)
    }
}