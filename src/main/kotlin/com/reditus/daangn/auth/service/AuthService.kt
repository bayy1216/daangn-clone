package com.reditus.daangn.auth.service

import com.reditus.daangn.auth.repository.MemberLoginRedisRepository
import com.reditus.daangn.core.jwt.JwtProvider
import com.reditus.daangn.core.jwt.JwtToken
import com.reditus.daangn.core.jwt.MemberAuth
import com.reditus.daangn.member.repository.MemberRepository
import io.jsonwebtoken.JwtException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtProvider: JwtProvider,
    private val memberRepository: MemberRepository,
    private val memberLoginRedisRepository: MemberLoginRedisRepository,
) {
    fun login(email: String, password: String): JwtToken {
        val member = memberRepository.findByEmail(email) ?: throw IllegalArgumentException("존재하지 않는 이메일입니다.")
        member.validCheckInLogin()
        val failCount = memberLoginRedisRepository.getLoginFailCount(email) ?: 0

        if(failCount >= 5){
            val expireTime = memberLoginRedisRepository.getExpireTime(email)
            throw IllegalArgumentException("로그인에 5회 이상 실패하였습니다. ${expireTime}분 후 다시 시도해주세요.")
        }

        if(member.password != password){
            memberLoginRedisRepository.setLoginFailCount(email, failCount + 1)
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다. 로그인에 ${failCount + 1}회 실패하였습니다.")
        }
        memberLoginRedisRepository.deleteLoginFailCount(email)



        val memberAuth = MemberAuth(member.id!!, member.type)
        return jwtProvider.createToken(memberAuth)
    }

    fun refresh(token: String): JwtToken {
        if(!jwtProvider.validateToken(token)){
            throw JwtException("토큰이 유효하지 않습니다.")
        }
        val memberAuth = jwtProvider.extractMemberAuth(token)
        return jwtProvider.createToken(memberAuth)
    }
}