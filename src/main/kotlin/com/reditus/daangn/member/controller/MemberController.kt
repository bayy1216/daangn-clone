package com.reditus.daangn.member.controller

import com.reditus.daangn.auth.service.AuthService
import com.reditus.daangn.core.controller.interceptor.annotation.JwtFilterExclusion
import com.reditus.daangn.core.controller.interceptor.annotation.Login
import com.reditus.daangn.core.jwt.JwtToken
import com.reditus.daangn.core.jwt.MemberAuth
import com.reditus.daangn.member.controller.dto.request.EmailSignupRequest
import com.reditus.daangn.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/members")
class MemberController(
    private val memberService: MemberService,
    private val authService: AuthService,
) {

    @JwtFilterExclusion
    @PostMapping
    fun createMember(@Valid @RequestBody request: EmailSignupRequest) : JwtToken{
        memberService.createMember(request)
        return authService.login(request.email, request.password)
    }


    @DeleteMapping
    fun deleteMember(@Login memberAuth: MemberAuth){
        memberService.deleteMember(memberAuth.id)
    }
}