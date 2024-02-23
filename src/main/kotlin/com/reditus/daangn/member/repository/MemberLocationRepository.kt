package com.reditus.daangn.member.repository

import com.reditus.daangn.member.entity.MemberLocation
import org.springframework.data.jpa.repository.JpaRepository

interface MemberLocationRepository: JpaRepository<MemberLocation, Long> {
}