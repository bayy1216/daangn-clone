package com.reditus.daangn.member.repository

import com.reditus.daangn.member.entity.MemberSetting
import org.springframework.data.jpa.repository.JpaRepository

interface MemberSettingRepository: JpaRepository<MemberSetting, Long> {
}