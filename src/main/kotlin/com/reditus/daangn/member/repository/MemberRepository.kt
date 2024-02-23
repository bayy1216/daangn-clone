package com.reditus.daangn.member.repository

import com.reditus.daangn.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
}