package com.reditus.daangn.auth.service

import com.reditus.daangn.core.exception.ResourceNotFoundException
import com.reditus.daangn.core.jwt.JwtProvider
import com.reditus.daangn.core.jwt.JwtToken
import com.reditus.daangn.core.jwt.MemberAuth
import com.reditus.daangn.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtProvider: JwtProvider,
    private val memberRepository: MemberRepository,
) {
    fun login(email: String, password: String): JwtToken {
        val member = memberRepository.findByEmail(email) ?: throw ResourceNotFoundException("Member", email)
        if(member.password != password) throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        val memberAuth = MemberAuth(member.id!!, member.type)
        return jwtProvider.createToken(memberAuth)
    }
}