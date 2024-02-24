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
        val member = memberRepository.findByEmail(email) ?: throw IllegalArgumentException("존재하지 않는 이메일입니다.")
        if(member.password != password) throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")

        member.validCheck()

        val memberAuth = MemberAuth(member.id!!, member.type)
        return jwtProvider.createToken(memberAuth)
    }
}