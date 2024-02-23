package com.reditus.daangn.member.repository

import com.reditus.daangn.member.entity.MemberKeywordAlarm
import org.springframework.data.jpa.repository.JpaRepository

interface MemberKeywordAlarmRepository: JpaRepository<MemberKeywordAlarm, Long> {
}