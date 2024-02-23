package com.reditus.daangn.member.repository

import com.reditus.daangn.member.entity.MemberBan
import org.springframework.data.jpa.repository.JpaRepository

interface MemberBanRepository : JpaRepository<MemberBan, Long> {
}