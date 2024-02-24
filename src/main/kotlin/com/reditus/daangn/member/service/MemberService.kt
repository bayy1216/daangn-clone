package com.reditus.daangn.member.service

import com.reditus.daangn.core.utils.findByIdOrThrow
import com.reditus.daangn.member.controller.dto.request.EmailSignupRequest
import com.reditus.daangn.member.entity.Member
import com.reditus.daangn.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {
    @Transactional
    fun createMember(request: EmailSignupRequest): Long {
        val command = request.toCommand()
        if(memberRepository.findByEmail(command.email) != null) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }
        val member = Member.create(command)
        return memberRepository.save(member).id!!
    }

    @Transactional
    fun deleteMember(id: Long) {
        val member = memberRepository.findByIdOrThrow(id)
        member.delete()
    }


}